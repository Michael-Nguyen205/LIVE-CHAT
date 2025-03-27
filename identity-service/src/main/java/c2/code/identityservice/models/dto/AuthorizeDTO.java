package c2.code.identityservice.models.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AuthorizeDTO {

    private PermissionDTO permissonDTO;

}
