package c2.code.authenservice.models.response;


import c2.code.authenservice.entity.sql.Agent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgentResponse {


    private String id; // ID của người dùng
    private String name; // Tên người dùng
    private String email; // Địa chỉ email của người dùng




    public static AgentResponse toUserResponse(Agent agent) {
        if (agent == null) {
            return null; // Trả về null nếu user là null
        }

        return  AgentResponse.builder()
                .id(agent.getId().toString()) // Chuyển đổi ID thành String nếu cần
                .name(agent.getFullname())
                .email(agent.getEmail())
                .build();
    }
}
