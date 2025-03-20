package c2.code.wsservice.dto;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
    public  class ContactDTO {
    private String contactId;        // ID liên hệ
    private String name;               // Tên khách hàng
    private String email;              // Email khách hàng
    private String phone;              // Số điện thoại khách hàng
    private String ipContact;          // IP của khách hàng
    private String urlStart;           // URL khởi tạo cuộc trò chuyện
    private String priorityLanguage;   // Ngôn ngữ ưu tiên (ví dụ: "vi" cho tiếng Việt)
    private String comment;            // Bình luận từ khách hàng
    private Boolean isOnline;
    }