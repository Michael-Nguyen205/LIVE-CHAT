package c2.code.wsservice.message;

import c2.code.wsservice.dto.ContactDTO;
import c2.code.wsservice.dto.TagDTO;
import c2.code.wsservice.dto.TicketStatusDTO;
import c2.code.wsservice.dto.TicketTypeDTO;
import lombok.Data;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

@Data
public class M0300CreateTicketMessage extends BaseMessage {
    private String urlStart;
    private String name;
    private String email;
    private String phone;
    private ObjectId accountId;
    private List<TagDTO> tags;
    private M0300TicketTypeMessage ticketType;
    private M0300TicketStatusMessage status;
    private String subject;
    private ContactDTO contact;
    @Data
    public static class  M0300TicketTypeMessage {
        private ObjectId ticketTypeId;
        private String name;
    }

    @Data
    public static class M0300TicketStatusMessage {
        private ObjectId statusId;
        private String status;
    }


}
