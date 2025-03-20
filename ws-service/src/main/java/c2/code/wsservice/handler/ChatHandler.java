package c2.code.wsservice.handler;

import c2.code.wsservice.message.BaseMessage;
import c2.code.wsservice.message.ClientRequest;
import io.netty.channel.ChannelHandlerContext;


public interface ChatHandler {

    default void processMessage(String requestId, ClientRequest clientRequest, ChannelHandlerContext context) {

    }
}
