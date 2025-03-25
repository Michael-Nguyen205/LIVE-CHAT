package c2.code.authenservice.models.dto;

import lombok.*;

import java.util.List;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AuthorizeDTO {
    private PermissionDTO permissonResponse;

    private PermissionDTO permissonDTO;

}
