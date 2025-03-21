package c2.code.authenservice.repository;

import c2.code.authenservice.entity.departmentAgentAssignmentCondition.DepartmentAgentAssignmentCondition;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DepartmentAgentAssignmentConditionRepository extends BaseRepositoryMongo<DepartmentAgentAssignmentCondition, ObjectId> {
    // Các phương thức tìm kiếm tùy chỉnh nếu cần
}
