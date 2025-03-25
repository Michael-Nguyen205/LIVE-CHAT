package c2.code.authenservice.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString

@JsonInclude(JsonInclude.Include.NON_NULL)

public class FunctionDTO {
    private Integer id;

    private String name;



    @JsonProperty("actions")
    private List<ActionDTO> actionResponses;


}
