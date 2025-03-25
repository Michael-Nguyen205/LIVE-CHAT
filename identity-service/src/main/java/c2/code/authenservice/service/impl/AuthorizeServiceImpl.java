package c2.code.authenservice.service.impl;

import c2.code.authenservice.models.dto.*;
import c2.code.authenservice.models.mapping.PermissionFunctionActionDepartmentMapping;
import c2.code.authenservice.service.IAuthorizeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service("authorizeServiceImpl")
public class AuthorizeServiceImpl implements IAuthorizeService {

    private final DatabaseClient databaseClient;








    @Override
    public Mono<AuthorizeDTO> getAuthor(String permissionId) {
        String query = """
            SELECT p.id AS permission_id,
                   p.name AS permission_name,
                   f.id AS function_id,
                   f.name AS function_name,
                   a.id AS action_id,
                   a.name AS action_name
            FROM permission_function_action_department pfad
            LEFT JOIN public."function" f ON pfad.function_id = f.id
            LEFT JOIN actions a ON pfad.action_id = a.id
            JOIN permissions p ON pfad.permission_id = p.id
            WHERE p.id =
        """ + permissionId;

        return databaseClient.sql(query)
                .map(PermissionFunctionActionDepartmentMapping::mapRowToMapping)
                .all()
                .collectList()
                .flatMap(this::mapToAuthorizeDTO);
    }




    private Mono<AuthorizeDTO> mapToAuthorizeDTO(List<PermissionFunctionActionDepartmentMapping> mappings) {
        if (mappings.isEmpty()) {
            return Mono.empty();
        }

        PermissionFunctionActionDepartmentMapping firstMapping = mappings.iterator().next();
        PermissionDTO permissionDTO = new PermissionDTO(
                firstMapping.getPermissionId(),
                firstMapping.getPermissionName(),
                mapToFunctionDTOList(mappings)
        );

        AuthorizeDTO authorizeDTO = new AuthorizeDTO();
        authorizeDTO.setPermissonDTO(permissionDTO);
        return Mono.just(authorizeDTO);
    }

    private List<FunctionDTO> mapToFunctionDTOList(List<PermissionFunctionActionDepartmentMapping> mappings) {
        return mappings.stream()
                .collect(Collectors.groupingBy(PermissionFunctionActionDepartmentMapping::getFunctionId))
                .entrySet().stream()
                .map(entry -> mapToFunctionDTO(entry.getValue()))
                .collect(Collectors.toList());
    }

    private FunctionDTO mapToFunctionDTO(List<PermissionFunctionActionDepartmentMapping> mappings) {
        PermissionFunctionActionDepartmentMapping firstMapping = mappings.iterator().next();
        List<ActionDTO> actions = mapToActionDTOs(mappings);
        return new FunctionDTO(firstMapping.getFunctionId(), firstMapping.getFunctionName(), actions);
    }

    private List<ActionDTO> mapToActionDTOs(List<PermissionFunctionActionDepartmentMapping> mappings) {
        return mappings.stream()
                .collect(Collectors.groupingBy(PermissionFunctionActionDepartmentMapping::getActionId))
                .entrySet().stream()
                .map(entry -> {
                    PermissionFunctionActionDepartmentMapping firstMapping = entry.getValue().get(0);
                    return new ActionDTO(firstMapping.getActionId(), firstMapping.getActionName() , firstMapping.getDepartmentId());
                })
                .collect(Collectors.toList());
    }
}
