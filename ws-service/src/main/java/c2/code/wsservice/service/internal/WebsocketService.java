package c2.code.wsservice.service.internal;

import c2.code.wsservice.cache.ChannelContextCache;
import c2.code.wsservice.message.BaseResponse;
import com.google.gson.Gson;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class WebsocketService {

    @Autowired
    private ChannelContextCache channelContextCache;
    public static final Gson gson = new Gson();

    public void send(String userId, BaseResponse response) {
        ChannelHandlerContext context = channelContextCache.get(userId);
        if (Objects.isNull(context)) {
            log.info("User id was offline: {}", userId);
            return;
        }
        context.channel().writeAndFlush(new TextWebSocketFrame(gson.toJson(response)));
    }
}
