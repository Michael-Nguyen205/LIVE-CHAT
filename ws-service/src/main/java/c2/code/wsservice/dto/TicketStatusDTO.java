package c2.code.wsservice.dto;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
    public  class TicketStatusDTO {
        private ObjectId statusId;
        private String status;
    }