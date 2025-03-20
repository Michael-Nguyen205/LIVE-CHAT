package c2.code.wsservice.handler;

import c2.code.api.CommonResult;
import c2.code.dto.request.LoginRequest;
import c2.code.dto.response.LoginResponse;
import c2.code.wsservice.cache.ChannelContextCache;
import c2.code.wsservice.message.ClientRequest;
import c2.code.wsservice.message.M0100LoginMessage;
import c2.code.wsservice.message.M0100LoginResponse;
import c2.code.wsservice.service.api.AuthService;
import c2.code.wsservice.util.ContextUtils;
import c2.code.wsservice.util.RequestUtils;
import ch.qos.logback.core.util.ContextUtil;
import com.google.gson.Gson;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

// /api/login
// mid0100 -> login
@Service(value = "mid0100")
public class M0100LoginHandler implements ChatHandler {

    private static final Logger logger = LoggerFactory.getLogger(M0100LoginHandler.class);
    Gson gson = new Gson();

    @Autowired
    ChannelContextCache contextCache;
    @Autowired
    private RequestUtils requestUtils;
    @Autowired
    private AuthService authService;

    @Value("${chat.nodeId}")
    int serverId;

    @Override
    public void processMessage(String requestId, ClientRequest clientRequest, ChannelHandlerContext context) {
        logger.info("[{}] Start process login request. requestId: {}. Message: {}", requestId, requestId, clientRequest.getContent());
        M0100LoginMessage request = requestUtils.buildRequest(requestId, clientRequest, M0100LoginMessage.class);

        // Gọi sang authen service để check login
        LoginRequest loginRequest = buildRequest(request);
        logger.info("[{}] Login request {}", requestId, gson.toJson(loginRequest));
        CommonResult<LoginResponse> loginResponse = authService.login(loginRequest);
        logger.info("[{}] Login response {}", requestId, gson.toJson(loginResponse));

        M0100LoginResponse response = new M0100LoginResponse();
        response.init(request, loginResponse);

        if (loginResponse.isSuccess()) {
            logger.info("[{}] Login success. Put user id to context & set to cache {}", requestId, clientRequest.getUserId());
            ContextUtils.putUserIdToContext(context, clientRequest.getUserId());
            contextCache.set(clientRequest.getUserId(), context);
            LoginResponse responseData = loginResponse.getData();
            response.setName(responseData.getName());
            response.setAvatarUrl(responseData.getAvatarUrl());
            response.setThumbAvatarUrl(responseData.getThumbAvatarUrl());
        }
        context.channel().writeAndFlush(new TextWebSocketFrame(gson.toJson(response)));
    }

    private LoginRequest buildRequest(M0100LoginMessage request) {
        LoginRequest loginRequest = new LoginRequest();
        BeanUtils.copyProperties(request, loginRequest);
        return loginRequest;
    }
}

