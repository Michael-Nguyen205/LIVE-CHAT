package c2.code.authenservice.service.impl;

import c2.code.authenservice.entity.departmentAgentAssignmentCondition.DepartmentAgentAssignmentCondition;
import c2.code.authenservice.repository.DepartmentAgentAssignmentConditionRepository;
import c2.code.authenservice.service.IDepartmentAgentAssignmentConditionService;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;



@Service
public class DepartmentAgentAssignmentServiceImpl extends BaseServiceMongoImpl<DepartmentAgentAssignmentCondition, ObjectId, DepartmentAgentAssignmentConditionRepository> implements IDepartmentAgentAssignmentConditionService {
//
//
//@Autowired
//private ActionsRepository actionsRepository;

    public DepartmentAgentAssignmentServiceImpl(DepartmentAgentAssignmentConditionRepository repository) {
        super(repository); // Truyền repository vào lớp cha
    }

}
