package c2.code.authenservice.repository;

import c2.code.authenservice.entity.sql.PermissionFunctionActionDepartment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PermissionFuntionActionDepartmentRepository extends BaseRepositoryReactive<PermissionFuntionActionDepartmentRepository, String> {


    @Query(value = "SELECT EXISTS (" +
            "SELECT 1 " +
            "FROM public.agent_permission ap " +
            "JOIN public.agent a ON ap.agent_id = a.id " +
            "JOIN public.permission p ON ap.permission_id = p.id " +
            "JOIN public.permission_function_action_department pfad ON pfad.permission_id = p.id " +
            "JOIN public.\"function\" f ON pfad.function_id = f.id " +
            "WHERE a.email = :email " +
            "AND pfad.action_id = :actionId " +
            "AND pfad.function_id = :functionId " +
            "AND (:departmentId IS NULL OR pfad.department_id = :departmentId))",
            nativeQuery = true)
    Mono<Boolean> hasAuthor(@Param("email") String email,
                                @Param("actionId") String actionId,
                                @Param("functionId") String functionId,
                                @Param("departmentId") Integer departmentId);

}
