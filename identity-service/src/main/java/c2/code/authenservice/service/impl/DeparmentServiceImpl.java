package c2.code.authenservice.service.impl;

import c2.code.authenservice.entity.sql.Agent;
import c2.code.authenservice.entity.sql.AgentDepartment;
import c2.code.authenservice.entity.sql.Department;
import c2.code.authenservice.enums.ErrorCodeEnum;
import c2.code.authenservice.exceptions.AppException;
import c2.code.authenservice.models.request.CreateDepartmentRequest;
import c2.code.authenservice.models.request.SignUpSubAgentRequest;
import c2.code.authenservice.repository.AgentDepartmentReporitory;
import c2.code.authenservice.repository.AgentRepository;
import c2.code.authenservice.repository.DepartmentRepository;
import c2.code.authenservice.service.IDepartmentService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor

@Service("deparmentServiceImpl")  // Đặt tên cho bean
public class DeparmentServiceImpl implements IDepartmentService {

    private  final AgentDepartmentReporitory agentDepartmentReporitory;
    private final DepartmentRepository departmentRepository;




    @Override
    @Transactional
    @PreAuthorize("@authorUtils.hasAuthor('VIEW','PERMISSION',null)")

    public Void createDepartment(CreateDepartmentRequest request) {
        try {
            // 1. Tạo Department
            Department department = Department.builder()
                    .name(request.getName() != null ? request.getName() : "")
                    .companyId(request.getCompanyId())
                    .build();

            // Lưu Department vào cơ sở dữ liệu
            departmentRepository.save(department);

            // 2. Tạo danh sách AgentDepartment
            List<AgentDepartment> agentDepartments = request.getAgentIds().stream()
                    .filter(Objects::nonNull) // Loại bỏ null và UUID "0"
                    .map(agentId -> {
                        // Tạo AgentDepartment mới cho mỗi agentId
                        AgentDepartment agentDepartment = new AgentDepartment();
                        agentDepartment.setAgentId(agentId);
                        agentDepartment.setPermissionId(department.getId());  // Gán department ID vào permissionId
                        return agentDepartment;
                    })
                    .collect(Collectors.toList());

            // 3. Lưu AgentDepartment vào cơ sở dữ liệu
            agentDepartmentReporitory.saveAll(agentDepartments);

            return null;  // Không cần trả giá trị vì phương thức trả về Void

        } catch (AppException e) {
            // Xử lý ngoại lệ và ném lại ngoại lệ nếu cần thiết
            throw e;
        } catch (Exception e) {
            // Xử lý ngoại lệ chung nếu có lỗi ngoài AppException
            throw new AppException(ErrorCodeEnum.DATA_NOT_FOUND, e);
        }
    }


//    private void validateEmailNotExist(String email) {
//        if (agentRepository.existsByEmail(email)) {
//            throw new AppException(ErrorCodeEnum.USER_EXISTED, "Email already exists");
//        }
//    }

}