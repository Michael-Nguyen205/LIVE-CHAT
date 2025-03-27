//package c2.code.identityservice.dao;
//
//import c2.code.identityservice.entity.UserEntity;
//import c2.code.dto.UserCache;
//import com.google.gson.Gson;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.concurrent.TimeUnit;
//
//@Service
//@Slf4j
//public class UserRedisDao {
//
//    @Autowired
//    private StringRedisTemplate redisTemplate;
//    private Gson gson = new Gson();
//    public static final String USER_KEY_PREFIX = "user:";
//    public static final String USER_SESSION_PREFIX = "user_session:";
//    @Value("${chat.session.ttl:60}")
//    long ttl;
//
//    public void putUser(String userId, UserCache user) {
//        log.info("Put user to redis {}", userId);
//        redisTemplate.opsForValue().set(USER_KEY_PREFIX + userId, gson.toJson(user));
//    }
//
//    public UserCache getUser(String userId) {
//        log.info("Get user from redis {}", userId);
//        String userJson = redisTemplate.opsForValue().get(USER_KEY_PREFIX + userId);
//        return gson.fromJson(userJson, UserCache.class);
//    }
//
//    public void putUserSession(String userId, String session) {
//        log.info("Set user session. User: {}", userId);
//        redisTemplate.opsForValue().set(USER_SESSION_PREFIX + userId, session);
//        redisTemplate.expire(USER_SESSION_PREFIX + userId, ttl, TimeUnit.MINUTES);
//    }
//
//    public String getUserSession(String userId) {
//        log.info("Get user session. User: {}", userId);
//        return redisTemplate.opsForValue().get(USER_SESSION_PREFIX + userId);
//    }
//}
