package c2.code.authenservice.models.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateAuthorRequest {

    @JsonProperty("permission_id")
    private Integer id;

    @JsonProperty("permission_name")
    private String name;

    @NotEmpty(message = "function_id_list cannot be blank")
    @JsonProperty("function_id_list") // Đổi tên từ 'funtion_id_list' thành 'function_id_list'
    private List<CreateFunctionRequest> functionRequestList; // Sửa 'funtionRequestList' thành 'functionRequestList'

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class CreateFunctionRequest {
        @JsonProperty("function_id") // Sửa từ 'funtion_id' thành 'function_id'
        private Integer id;

        //    @NotEmpty(message = "actionRequestList cannot be blank")
        @Valid
        @JsonProperty("action_id_list")
        private List<CreateActionRequest> actionRequestList;

        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        @Data
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class CreateActionRequest {
            @JsonProperty("action_id")
            private Integer actionId;

            @Valid
            @JsonProperty("deparment_id_list")
            private List<UUID> deparmentRequestList;

            @JsonProperty("is_for_agent")
            private Boolean isForAgent ;
        }
    }
}
