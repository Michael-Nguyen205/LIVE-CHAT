package c2.code.authenservice.repository;

import c2.code.authenservice.entity.nosql.departmentAgentAssignmentCondition.DepartmentAgentAssignmentCondition;
import org.bson.types.ObjectId;

public interface DepartmentAgentAssignmentConditionRepository extends BaseRepositoryMongo<DepartmentAgentAssignmentCondition, ObjectId> {
    // Các phương thức tìm kiếm tùy chỉnh nếu cần
}
