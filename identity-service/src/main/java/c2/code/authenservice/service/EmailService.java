package c2.code.authenservice.service;

import c2.code.authenservice.enums.ErrorCodeEnum;
import c2.code.authenservice.exceptions.AppException;
import c2.code.authenservice.models.request.EmailRequestDTO;
import c2.code.authenservice.repository.https_client.EmailClient;
import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {
   private final EmailClient emailClient;

    @Value("jijiji")
    @NonFinal
    String apiKey;
    public String sendPassWord( String password , String email ) {

        // Tạo đối tượng Sender
        EmailRequestDTO.SenderDTO sender = new EmailRequestDTO.SenderDTO();
        sender.setName("Devteria DotCom");
        sender.setEmail("devteriadotcom@gmail.com");

        // Tạo đối tượng người nhận
        EmailRequestDTO.RecipientDTO recipientDTO = new EmailRequestDTO.RecipientDTO();
        recipientDTO.setName(order.getUserId());
        recipientDTO.setEmail(email);



        // Tạo đối tượng EmailRequest
        EmailRequestDTO emailRequest = new EmailRequestDTO();
        emailRequest.setSender(sender);
        emailRequest.setTo(List.of(recipientDTO));
        emailRequest.setSubject("hihi");
        emailRequest.setHtmlContent(createHtmlContentPayEmail(order)); // Thiết lập nội dung email (thêm nếu cần)

        try {
            return emailClient.sendEmail(apiKey, emailRequest);
        } catch (FeignException e){
            throw new AppException(ErrorCodeEnum.CANNOT_SEND_EMAIL);
        }
    }



    String createHtmlContentPayEmail(Order order){
        return "url";
    }

}