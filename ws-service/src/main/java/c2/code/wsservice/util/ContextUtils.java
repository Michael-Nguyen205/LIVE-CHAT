package c2.code.wsservice.util;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.AttributeKey;
import org.springframework.util.StringUtils;

import java.net.InetSocketAddress;

public class ContextUtils {
    private static final String CONTEXT_KEY_USER_ID = "user";

    private static void putValueToContext(ChannelHandlerContext context, String key, String value) {
        if (StringUtils.hasText(key) && StringUtils.hasText(value)) {
            context.channel().attr(AttributeKey.valueOf(key)).set(value);
        }
    }

    private static String getValueFromContext(ChannelHandlerContext context, String key) {
        return (String) context.channel().attr(AttributeKey.valueOf(key)).get();
    }

    public static void putUserIdToContext(ChannelHandlerContext context, String userId) {
        putValueToContext(context, CONTEXT_KEY_USER_ID, userId);
    }

    public static String getUserIdFromContext(ChannelHandlerContext context) {
        return getValueFromContext(context, CONTEXT_KEY_USER_ID);
    }


}
