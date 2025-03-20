package c2.code.wsservice.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.netty.channel.ChannelHandlerContext;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.Duration;


@Service
public class ChannelContextCache {

    Cache<String, ChannelHandlerContext> channelCache;

    @PostConstruct
    void init() {
        channelCache = Caffeine.newBuilder()
                .maximumSize(1_000_000)
                .expireAfterAccess(Duration.ofMinutes(60))
                .build();
    }

    public void set(String user, ChannelHandlerContext context) {
        channelCache.put(user, context);
    }

    public ChannelHandlerContext get(String user) {
        return channelCache.getIfPresent(user);
    }

    public void remove(String user) {
        channelCache.invalidate(user);
    }
}
