package c2.code.authenservice.models.dto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ActionDTO {

    private Integer Id;

    private String name;

//    private boolean isForDepartment;

    private String deparmentId;

}
