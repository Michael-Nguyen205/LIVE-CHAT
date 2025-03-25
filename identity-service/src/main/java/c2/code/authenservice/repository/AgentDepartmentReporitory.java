package c2.code.authenservice.repository;

import c2.code.authenservice.entity.sql.Agent;
import c2.code.authenservice.entity.sql.AgentDepartment;
import c2.code.authenservice.entity.sql.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AgentDepartmentReporitory extends BaseRepositoryJpa<AgentDepartment, Integer> {



}
