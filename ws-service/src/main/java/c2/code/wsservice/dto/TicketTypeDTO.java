package c2.code.wsservice.dto;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
    public  class TicketTypeDTO {
        private ObjectId ticketTypeId;
        private String name;
    }
