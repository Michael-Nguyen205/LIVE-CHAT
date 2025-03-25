package spring.boot.messageservice.consumer;

import c2.code.dto.mid.M0200ChatData;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;


@Log4j2
//@RequiredArgsConstructor
@Service
public class NotificationConsumer {


    @RetryableTopic(
            attempts = "5",  // Number of retries
            backoff = @Backoff(delay = 2000, multiplier = 2),  // Initial delay 2 seconds, doubling each retry
            dltTopicSuffix = ".DLT"  // Dead Letter Topic suffix
    )



    @KafkaListener(topics = "notification", groupId = "notification")
    public void processNotification(M0200ChatData<?> messageDTO) {
        try {
            if (messageDTO == null) {
                throw new IllegalArgumentException("MessageDTO cannot be null");
            }

            log.error("messageDTO {}:",messageDTO);

            Notification notification = notificationFactory.createNotification(messageDTO);
            NotificationType type = NotificationType.fromNotificationType(messageDTO.getType());


            log.error("notification {}:",notification);
            log.error("type {}:",type);




//            NotificationManager notificationManager = notificationManagerProvider.getNotificationManager(type);
//            if (notificationManager != null) {
                notificationManagerr.send(notification,type);
//            } else {
//                log.warn("No NotificationManager found for type: {}", type);
//            }
            log.info("Successfully processed notification: {}", messageDTO);
        } catch (Exception e) {
            log.error("Failed to process notification", e);
            throw e;
        }
    }


//    @KafkaListener(topics = "notification", groupId = "notification")
//    public void processNotification(MessageDTO<?> messageDTO) {
//        try {
//            Notification notification = NotificationFactory.createNotification(messageDTO);
//            NotificationManager notificationManager;
//            if (notification instanceof EmailNotification) {
//                notificationManager = new EmailServiceManager();
//                notificationManager.send(notification);
//            } else if (notification instanceof SmsNotification) {
////                smsService.sendNotification(notification);
//            } else {
//                log.warn("Unknown notification type: {}", messageDTO.getType());
//            }
//            log.info("Successfully processed notification: {}", messageDTO);
//        } catch (Exception e) {
//            log.error("Failed to process notification", e);
//            throw e; // Rethrow to trigger retry mechanism
//        }
//    }





    @KafkaListener(id = "dltGroup", topics = "notification.DLT")
    public void listenToStatisticDLT(ConsumerRecord<String, String> record) {
        String message = record.value();
        log.info("Received message from DLT: {}", message);
        // Additional processing logic here
    }

     //docker exec -it kafka-broker-01 /bin/bash     vào container đó
    // kafka-topics --list --bootstrap-server localhost:9092    liệt kê các toppic
    // kafka-console-consumer --topic notification --bootstrap-server localhost:9092 --from-beginning đọc tin nắhn


}
