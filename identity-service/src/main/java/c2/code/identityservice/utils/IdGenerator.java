package c2.code.identityservice.utils;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Enumeration;

@Component
@Slf4j
public class IdGenerator {

    private static final int NODE_ID_BITS = 10;
    private static final int SEQUENCE_BITS = 12;

    private static final int MAX_NODE_ID = (int) (Math.pow(2, NODE_ID_BITS) - 1);
    private static final int MAX_SEQUENCE = (int) (Math.pow(2, SEQUENCE_BITS) - 1);

    private static final long CUSTOM_EPOCH = 1732116366389L;
    

    private volatile long lastTimestamp = -1L;
    private volatile long sequence = 0L;
    
    @Value("${chat.nodeId:-1}")
    int nodeId;
    
    @PostConstruct
    void configNodeID(){
        if (nodeId > MAX_NODE_ID) {
            throw new IllegalArgumentException(String.format("NodeId must be between %d and %d", 0, MAX_NODE_ID));
        }
        if(nodeId < 0){
            nodeId = createNodeId();
            log.info("Node Id < 0, generate node id: {}", nodeId);
        }
    }

    public synchronized long nextId() {
        long currentTimestamp = timestamp();

        if (currentTimestamp < lastTimestamp) {
            throw new IllegalStateException("Invalid System Clock!");
        }

        if (currentTimestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0) {
                // Sequence Exhausted, wait till next millisecond.
                currentTimestamp = waitNextMillis(currentTimestamp);
            }
        } else {
            // reset sequence to start with zero for the next millisecond
            sequence = 0;
        }
        lastTimestamp = currentTimestamp;

        long id = currentTimestamp << (NODE_ID_BITS + SEQUENCE_BITS);
        id |= (nodeId << SEQUENCE_BITS);
        id |= sequence;
        return id;
    }

    // Get current timestamp in milliseconds, adjust for the custom epoch.
    private static long timestamp() {
        return Instant.now().toEpochMilli() - CUSTOM_EPOCH;
    }

    // Block and wait till next millisecond
    private long waitNextMillis(long currentTimestamp) {
        while (currentTimestamp == lastTimestamp) {
            currentTimestamp = timestamp();
        }
        return currentTimestamp;
    }

    private int createNodeId() {
        int newNodeId;
        try {
            StringBuilder sb = new StringBuilder();
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                byte[] mac = networkInterface.getHardwareAddress();
                if (mac != null) {
                    for (int i = 0; i < mac.length; i++) {
                        sb.append(String.format("%02X", mac[i]));
                    }
                }
            }
            newNodeId = sb.toString().hashCode();
        } catch (SocketException ex) {
            newNodeId = (new SecureRandom().nextInt());
        }
        newNodeId = newNodeId & MAX_NODE_ID;
        return newNodeId;
    }
}
