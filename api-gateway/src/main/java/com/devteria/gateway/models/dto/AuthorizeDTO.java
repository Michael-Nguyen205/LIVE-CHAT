package com.devteria.gateway.models.dto;

import lombok.*;

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
