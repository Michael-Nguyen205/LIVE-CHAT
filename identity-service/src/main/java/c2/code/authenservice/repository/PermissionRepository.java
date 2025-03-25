package c2.code.authenservice.repository;


import c2.code.authenservice.entity.sql.Permission;
import reactor.core.publisher.Flux;
import java.util.List;

public interface PermissionRepository extends BaseRepositoryReactive<Permission, Integer> {
    Flux<Permission> findByIdIn(List<Integer> id);
    
}
