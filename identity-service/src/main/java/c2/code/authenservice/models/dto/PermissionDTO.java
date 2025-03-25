package c2.code.authenservice.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)

public class PermissionDTO {


    private Integer id;
    private String name;
    private List<FunctionDTO> modelResponses;
}

