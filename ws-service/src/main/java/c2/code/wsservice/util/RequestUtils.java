package c2.code.wsservice.util;

import c2.code.wsservice.message.BaseMessage;
import c2.code.wsservice.request.ClientRequest;
import com.google.gson.Gson;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class RequestUtils {
    @Value("${chat.nodeId}")
    private int serverId;

//        private int serverId = 111;

    public static final Gson gson = new Gson();

//    public <T extends BaseMessage> T parseRequest(String requestId, String message, Class<T> clazz) {
//        T request = gson.fromJson(message, clazz);
//        request.setServerId(serverId + "");
//        request.setServerTime(System.currentTimeMillis());
//        request.setRequestId(requestId);
//        return request;
//    }

    public <T extends BaseMessage> T buildRequest(String requestId, ClientRequest clientRequest, Class<T> classType) {

        Instant utcNow = Instant.parse(clientRequest.getTimestamp());
        ZoneId systemZone = ZoneId.systemDefault();  // Lấy múi giờ của hệ thống (ví dụ: máy chủ ở Việt Nam, New York, London...)
        ZonedDateTime serverTime = utcNow.atZone(systemZone);

        T request = gson.fromJson(clientRequest.getContent().toString(), classType);
        request.setUserId(clientRequest.getUserId());
        request.setServerId(serverId + "");
        request.setServerTime(Instant.now().toString());
        request.setRequestId(requestId);
        request.setMid(clientRequest.getMid());
        request.setUserId(clientRequest.getUserId());
        request.setServerTime(serverTime.toString());
        request.setClientTime(clientRequest.getTimestamp());
        request.setDeviceId(clientRequest.getDeviceId());
        request.setBrowser(clientRequest.getBrowser());
        request.setChromeVersion(clientRequest.getChromeVersion());
        request.setCountry(clientRequest.getCountry());
        request.setIp(clientRequest.getIp());
        request.setLanguage(clientRequest.getLanguage());
        request.setUtc(clientRequest.getUtc());
        return request;
    }
}


//private String mid;
//private String userId;
//private String deviceId;
//private long timestamp;
//private String ip;
//private String browser;
//private String chromeVersion;
//private String utc;
//private String country;
//private String language;
//private String url;
//private Object content; // Dùng JsonNode để linh hoạt với JSON phức tạp


//@Data
//public class BaseMessage {
//
//    String requestId;
//    String mid;
//    String userId;
//    String serverId;
//    long serverTime;
//    long clientTime;
//    String deviceId;
//    String session;
//    String clientRequestId;
//}
