package c2.code.wsservice.channelHandler;

import c2.code.wsservice.request.ClientRequest;
import io.netty.channel.ChannelHandlerContext;


public interface ChatHandler {

    default void processMessage(String requestId, ClientRequest clientRequest, ChannelHandlerContext context) {

    }
}
