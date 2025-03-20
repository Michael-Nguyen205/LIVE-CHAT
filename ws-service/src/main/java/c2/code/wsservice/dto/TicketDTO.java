package c2.code.wsservice.dto;
import lombok.Data;
import org.bson.types.ObjectId;
import java.util.Date;
import java.util.List;
@Data
public class TicketDTO {

    private ObjectId id;
    private String code;
    private ObjectId accountId;
    private String priority;
    private List<ObjectId> participants;
    private ObjectId assignedAgent;
    private String createdAt;
    private Date updatedAt;
    private int replyCount;
    private List<TagDTO> tags;
    private TicketTypeDTO ticketType;
    private String lastMessage;
    private TicketStatusDTO ticketStatus;
    private ObjectId departmentId;
    private String createdBy;
    private String subject;
    private String description;
    private Date resolvedAt;
    private ObjectId lastUpdatedBy;
    private boolean isDeleted;
    private boolean isSpam;
    private Boolean isActive;
    private ContactDTO contact;
    // Nested DTO classes
}
