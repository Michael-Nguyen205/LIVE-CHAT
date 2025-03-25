package c2.code.wsservice.websocket;

import c2.code.api.ResultCode;
import c2.code.wsservice.cache.ChannelContextCache;
import c2.code.wsservice.config.MidHandlerConfig;
import c2.code.wsservice.channelHandler.ChatHandler;
import c2.code.wsservice.exceptions.AppException;
import c2.code.wsservice.message.BaseResponse;
import c2.code.wsservice.request.ClientRequest;
import c2.code.wsservice.util.ContextUtils;
import c2.code.wsservice.util.IdGenerator;
import com.google.gson.Gson;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.AttributeKey;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Log4j2
@ChannelHandler.Sharable
@Component
public class RequestRoutingHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    private static final Logger logger = LoggerFactory.getLogger(RequestRoutingHandler.class);
    public static final Gson gson = new Gson();

    @Autowired
    MidHandlerConfig handlerConfig;
    @Autowired
    IdGenerator idGenerator;
    @Autowired
    ChannelContextCache contextCache;

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
        if (ctx.channel().attr(AttributeKey.valueOf("user")).get() != null) {
            contextCache.remove(ctx.channel().attr(AttributeKey.valueOf("user")).get().toString());
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Xử lý ngoại lệ toàn cục
        logger.error("Exception caught: ", cause);
        // Gửi phản hồi lỗi cho client nếu cần
        if (cause instanceof AppException) {
            // Xử lý riêng nếu cần thiết
        } else {
            // Đóng kết nối nếu gặp lỗi không thể khôi phục
            ctx.close();
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext context, WebSocketFrame frame) throws Exception {

//        {
//            "mid": "0300",
//                "userId": "2119750023921664",
//                "timestamp": "2025-03-18T11:30:00Z",  // Thời gian theo UTC
//                "deviceId": "a907a87a-3713-5da7-aa3a-471cdfb271ef",
//                "content": {
//            "destId": "2119900972728320",
//                    "message": "hello world",
//                    "clientRequestId": "d70719f6-db35-5dcb"
//        },
//            "language": "en",
//                "country": "US",
//                "timezone": "UTC-5"
//        } ở trên client lấy "language": "en",
//                "country": "US",
//                "timezone": "UTC-5" kiểu gì
//

        try {
        if (frame instanceof TextWebSocketFrame) {
            String textRequest = ((TextWebSocketFrame) frame).text().trim();
            ClientRequest clientRequest = gson.fromJson(textRequest, ClientRequest.class);
            if (Objects.isNull(clientRequest)) {
                logger.error("Invalid request. Cannot parse to client request. Request: {}", textRequest);
                BaseResponse response = BaseResponse.fail(ResultCode.INVALID_FORMAT_REQUEST);
                context.writeAndFlush(new TextWebSocketFrame(gson.toJson(response)));
                return;
            }

            BaseResponse response = new BaseResponse();
            response.setMid(clientRequest.getMid());
            response.setRequestId(clientRequest.getMid() + idGenerator.nextId());

            if (!(StringUtils.hasText(clientRequest.getMid()) &&
                    StringUtils.hasText(clientRequest.getUserId()) &&
                    Objects.nonNull(clientRequest.getContent()))) {
                logger.error("Invalid request. Missing required fields. Request: {}", textRequest);
                response = BaseResponse.fail(ResultCode.INVALID_FORMAT_REQUEST);
                context.writeAndFlush(new TextWebSocketFrame(gson.toJson(response)));
                return;
            }



            boolean isLoginRequest = "0100".equals(clientRequest.getMid());
            if (!isLoginRequest && !clientRequest.getUserId().equals(ContextUtils.getUserIdFromContext(context))) {
                logger.error("Invalid request. User must login before send other request. Request: {}", textRequest);
                response = BaseResponse.fail(ResultCode.USER_MUST_LOGIN);
                context.writeAndFlush(new TextWebSocketFrame(gson.toJson(response)));
                return;
            }

            ChatHandler chatHandler = handlerConfig.getHandlerByMid(clientRequest.getMid());
            if (Objects.isNull(chatHandler)) {
                logger.error("Cannot find handler. Please check message or mid configuration! Request: {}", textRequest);
                response = BaseResponse.fail(ResultCode.INVALID_FORMAT_REQUEST);
                context.writeAndFlush(new TextWebSocketFrame(gson.toJson(response)));
                return;
            }

            String requestId = clientRequest.getMid() + idGenerator.nextId();
            logger.debug("Forwarding to mid handler. RequestId:  {}", requestId);
            chatHandler.processMessage(requestId, clientRequest, context);
        } else {
            String message = "unsupported frame type: " + frame.getClass().getName();
            throw new UnsupportedOperationException(message);
        }
        } catch (Exception e) {
            logger.error("Error processing request in channelRead", e);
            // Xử lý ngoại lệ nếu có
            context.close(); // Đóng kết nối nếu có lỗi nghiêm trọng
        }
    }
}
