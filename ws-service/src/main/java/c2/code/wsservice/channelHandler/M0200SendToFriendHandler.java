package c2.code.wsservice.channelHandler;

import c2.code.api.CommonResult;
import c2.code.api.ResultCode;
import c2.code.request.CheckSendMessageRequest;
import c2.code.response.CheckSendMessageResponse;
import c2.code.wsservice.cache.ChannelContextCache;
import c2.code.wsservice.dto.M0200ChatDTO;
import c2.code.wsservice.request.ClientRequest;
import c2.code.wsservice.message.M0200SendToFriendMessage;
import c2.code.wsservice.message.M0200SendToFriendResponse;
import c2.code.wsservice.service.api.UserService;
import c2.code.wsservice.util.IdGenerator;
import c2.code.wsservice.util.RequestUtils;
import com.google.gson.Gson;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service(value = "mid0200")
public class M0200SendToFriendHandler implements ChatHandler {

    private static final Logger logger = LoggerFactory.getLogger(M0200SendToFriendHandler.class);
    Gson gson = new Gson();

    @Autowired
    ChannelContextCache contextCache;
    @Autowired
    private IdGenerator idGenerator;

    @Value("${chat.nodeId}")
    int serverId;
    @Autowired
    private RequestUtils requestUtils;

    @Autowired
    private UserService userService;

    @Override
    public void processMessage(String requestId, ClientRequest clientRequest, ChannelHandlerContext context) {
        logger.info("[{}] Start process send message request. requestId: {}. Message: {}", requestId, requestId, clientRequest);
        M0200SendToFriendMessage request = requestUtils.buildRequest(requestId, clientRequest, M0200SendToFriendMessage.class);

        // Kiểm tra bạn bè có tồn tại trong hệ thống không ?
        CheckSendMessageRequest canSendRequest = buildRequest(request);
        logger.info("[{}] Can send message to friend request {}", requestId, gson.toJson(canSendRequest));
        CommonResult<CheckSendMessageResponse> canSendResponse = userService.canSendMessage(canSendRequest);
        logger.info("[{}] Check send message response {}", requestId, gson.toJson(canSendResponse));

        M0200SendToFriendResponse response = new M0200SendToFriendResponse();
        response.init(request, canSendResponse);

        if (!canSendResponse.getData().isValid()) {
            logger.info("[{}] Can not send message to friend. Friend not found or blocked", requestId);
            response.setResultCode(ResultCode.INVALID_USER);
            context.channel().writeAndFlush(new TextWebSocketFrame(gson.toJson(response)));
            return;
        }

        // Sinh message id cho message
        request.setMessageId(idGenerator.nextId());
        response.setMessageId(request.getMessageId());

        // Gửi message qua kafka để lưu vào db

        // Gửi response về cho sender
        context.channel().writeAndFlush(new TextWebSocketFrame(gson.toJson(response)));

        // Gửi message qua websocket cho friend
        M0200ChatDTO chatData = new M0200ChatDTO();
        BeanUtils.copyProperties(request, chatData);
        chatData.setSenderId(request.getUserId());

        ChannelHandlerContext friendContext = contextCache.get(request.getDestId());
        if (Objects.nonNull(friendContext)) {
            friendContext.channel().writeAndFlush(new TextWebSocketFrame(gson.toJson(chatData)));
        }

    }

    private CheckSendMessageRequest buildRequest(M0200SendToFriendMessage request) {
        CheckSendMessageRequest checkSendMessageRequest = new CheckSendMessageRequest();
        checkSendMessageRequest.setRequestId(request.getRequestId());
        checkSendMessageRequest.setUserId(request.getUserId());
        checkSendMessageRequest.setDestId(request.getDestId());
        return checkSendMessageRequest;
    }
}
