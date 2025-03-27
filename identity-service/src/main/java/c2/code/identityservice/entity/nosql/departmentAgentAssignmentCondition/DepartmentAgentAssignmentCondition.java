package c2.code.identityservice.entity.nosql.departmentAgentAssignmentCondition;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
//Quy Tắc Phân Chia Ticket Cho Phòng Ban và agent
//-Khi admin tạo phòng ban admin sẽ có quyền cấu hình phòng ban này sẽ trỏ đến những điều kiện nào (VD: ticket_type(email,chatbox,facebook),chỉ định các Fanpage , email muốn trỏ đến , loại vấn đề issue , keyword)
//
//        -Sau khi chọn xong thì đến agent assigtment condition , điều kiện lọc agent này sẽ kế thừa toàn bộ condition của deparment assigtment
//
//-Khi thêm các agent mới vào phòng ban thì các condition của agent đó sẽ kế thừa từ  agent assigtment condition
//
//-Admin có thể cấu hình thêm Cho agent vài thông tin assigtment nữa  như các thông tin assigtment mà deparment assigtment đang thiếu  , ngôn ngữ và thuật toán phân chia agent (
//        Round-Robin
//        Load-Based
//        - load dựa trên số thời gian online , số ticket ít nhất hoàn thành , số tin nhắn chat ít nhất , đang rảnh ,
//  - mỗi 1 load sẽ cho lựa chọn chọn khoảng thời gian gần đây để tính các chỉ số của agent để phân ticket
//)



@Document(collection = "department_agent_assignment_condition")
public class DepartmentAgentAssignmentCondition {

    @Id
    private String id;
    private Integer departmentId;
    private String accountId;
    private List<IssueCondition> issueCondition;
    private List<KeywordCondition> keywordCondition;
    private List<TagCondition> tagsCondition;
    private List<Agent> agent;
    private List<LanguageAssignment> languageAssignments;
    private Email email;
    private FanpageFaceBook fanpageFaceBook ;


    private List<AssignmentAlgorithm> assignmentAlgorithm;


    @Getter
    public enum AssignmentAlgorithm {
        ROUND_ROBIN("Round-Robin"),
        LOAD_BASED("Load-Based"),
        PRIORITY("Priority");

        // Getter for description
        private final String description;

        // Constructor
        AssignmentAlgorithm(String description) {
            this.description = description;
        }

        // Method to get the assignment algorithm based on a string
        public static AssignmentAlgorithm fromString(String text) {
            for (AssignmentAlgorithm algorithm : AssignmentAlgorithm.values()) {
                if (algorithm.description.equalsIgnoreCase(text)) {
                    return algorithm;
                }
            }
            throw new IllegalArgumentException("No enum constant with description " + text);
        }
    }

    // Getters and Setters
}
