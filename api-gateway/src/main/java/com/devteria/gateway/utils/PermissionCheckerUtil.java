package com.devteria.gateway.utils;

import com.devteria.gateway.models.dto.AuthorizeDTO;
import com.devteria.gateway.models.dto.FunctionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;


@Component
@RequiredArgsConstructor
public class PermissionCheckerUtil {

    public Mono<Boolean> hasRoleForPath(AuthorizeDTO authorizeDTO, String path) {
        // Lấy các claims từ JWT token (giả sử claims được lấy từ authorizeDTO hoặc JWT)
        List<FunctionDTO> functions = authorizeDTO.getPermissonResponse().getModelResponses();

        // Kiểm tra quyền cho từng API path
        if (path.equals("/api/v1/getDetailCustomer/**")) {
            // Kiểm tra quyền liên quan đến "VIEW_CUSTOMER"
            return Mono.just(functions.stream()
                    .anyMatch(function -> function.getName().equals("VIEW_CUSTOMER") &&
                            function.getActionResponses().stream()
                                    .anyMatch(action -> action.getName().equals("VIEW") && action.getDeparmentId() != null)
                    ));
        } else if (path.equals("/customer/getDetailCustomer/**")) {
            // Kiểm tra quyền liên quan đến "VIEW_PERMISSION"
            return Mono.just(functions.stream()
                    .anyMatch(function -> function.getName().equals("VIEW_PERMISSION") &&
                            function.getActionResponses().stream()
                                    .anyMatch(action -> action.getName().equals("VIEW") && action.getDeparmentId() != null)
                    ));
        }

        return Mono.just(false); // Không có quyền nếu không khớp với bất kỳ đường dẫn nào
    }
}