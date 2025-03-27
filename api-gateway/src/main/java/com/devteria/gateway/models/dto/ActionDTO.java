package com.devteria.gateway.models.dto;
import lombok.*;

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
