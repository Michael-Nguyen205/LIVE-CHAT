package c2.code.wsservice.dto;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
    public  class TagDTO {
        private ObjectId tagId;
        private String name;
        private String colorCode;
    }