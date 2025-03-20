package c2.code.wsservice.config;

import c2.code.wsservice.channelHandler.ChatHandler;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
//@ConfigurationProperties(prefix = "")
//@PropertySource(value = "classpath:/mid-config.yml", factory = YamlPropertySourceFactory.class)
public class MidHandlerConfig {

    private List<MidEntity> handlers;
    private Map<String, MidEntity> mapHandler;

    private static final Logger logger = LoggerFactory.getLogger(MidHandlerConfig.class);
    private static final String MID_NAME_PREFIX = "mid";

    @Autowired
    private ApplicationContext context;

    public List<MidEntity> getHandlers() {
        return handlers;
    }

    public void setHandlers(List<MidEntity> handlers) {
        this.handlers = handlers;
    }

    @PostConstruct
    void init() {
        mapHandler = new HashMap<>();
        handlers = new ArrayList<>();
        handlers.add(new MidEntity("0300", "M0300CreateTicketHandlerByContact"));
        handlers.add(new MidEntity("0100", "M0100LoginHandler"));
        handlers.add(new MidEntity("0200", "M0200SendToFriendHandler"));
        handlers.add(new MidEntity("0201", "M0200SendToGroupHandler"));
        logger.info("====Initilizing all mid handlers===");
        for (MidEntity entity : handlers) {
            logger.info("MID: {}, Message type: {}", entity.getMid(), entity.getMessageType());
            mapHandler.put(entity.getMid(), entity);
        }
        logger.info("Total of handler: {}", handlers.size());
        logger.info("===================================");
    }

    public String getHandlerBeanName(String mid) {
        return MID_NAME_PREFIX + mid;
    }

    public ChatHandler getHandlerByMid(String mid) {
        String beanName = getHandlerBeanName(mid);
        if (context.getBean(beanName) == null) {
            logger.info("Bean Handler not found. bean name: {}", beanName);
            return null;
        }

        if (context.getBean(beanName) instanceof ChatHandler) {
            return (ChatHandler) context.getBean(beanName);
        } else {
            logger.info("Handler is not an instance of VnchatHandler. bean name: {}", beanName);
            return null;
        }
    }

}
