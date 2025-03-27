package c2.code.identityservice.service.impl;

import c2.code.identityservice.entity.sql.AgentDepartment;
import c2.code.identityservice.entity.sql.Department;
import c2.code.identityservice.enums.ErrorCodeEnum;
import c2.code.identityservice.exceptions.AppException;
import c2.code.identityservice.models.request.CreateDepartmentRequest;
import c2.code.identityservice.repository.AgentDepartmentReporitory;
import c2.code.identityservice.repository.DepartmentRepository;
import c2.code.identityservice.service.IDepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service("deparmentServiceImpl")  // Đặt tên cho bean
public class DeparmentServiceImpl implements IDepartmentService {

    private final AgentDepartmentReporitory agentDepartmentReporitory;
    private final DepartmentRepository departmentRepository;

    @Override
    @PreAuthorize("@authorUtils.hasAuthor('VIEW','PERMISSION',null)")
    public Mono<Void> createDepartment(CreateDepartmentRequest request) {
        return Mono.just(request)
                // 1. Tạo Department và lưu vào cơ sở dữ liệu
                .flatMap(req -> {
                    Department department = Department.builder()
                            .name(req.getName() != null ? req.getName() : "")
                            .companyId(req.getCompanyId())
                            .build();
                    return departmentRepository.save(department);
                })
                // 2. Tạo danh sách AgentDepartment
                .flatMap(department -> {
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
                    return agentDepartmentReporitory.saveAll(agentDepartments).then();
                })
                // Xử lý ngoại lệ chung nếu có lỗi
                .onErrorMap(e -> new AppException(ErrorCodeEnum.DATA_NOT_FOUND, e));
    }
}
