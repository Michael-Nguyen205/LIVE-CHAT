package c2.code.identityservice.service.impl;

import c2.code.identityservice.entity.sql.Agent;
import c2.code.identityservice.enums.ErrorCodeEnum;
import c2.code.identityservice.exceptions.AppException;
import c2.code.identityservice.models.request.SignUpSubAgentRequest;
import c2.code.identityservice.repository.AgentRepository;
import c2.code.identityservice.service.IAgentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Log4j2
@Service("agentServiceImpl")  // Đặt tên cho bean
public class AgentServiceImpl extends BaseServiceReactiveImpl<Agent, UUID, ReactiveCrudRepository< Agent,UUID>>  implements IAgentService {

    private  final AgentRepository agentRepository;
    private final PasswordEncoder passwordEncoder;


    // Constructor truyền PostsRepository vào BaseServiceImpl
    public AgentServiceImpl(AgentRepository actionsRepository, AgentRepository agentRepository, PasswordEncoder passwordEncoder) {
        super(actionsRepository); // Truyền repository vào lớp cha
//        this.postsRepository = postsRepository; // Gán repository cho biến instance
        this.agentRepository = agentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Void createUser(SignUpSubAgentRequest request) {


        try {

            if(request.getFullname() == null ||request.getFullname().equals("")){
                throw new AppException(ErrorCodeEnum.NULL_POINTER);
            }
            if(request.getCompanyId() == null ||request.getCompanyId().equals("")){
                throw new AppException(ErrorCodeEnum.NULL_POINTER);
            }

            if(request.getEmail() == null ||request.getEmail().equals("")){
                throw new AppException(ErrorCodeEnum.NULL_POINTER);
            }


            UUID permissionId = request.getPermissionId();
            if (permissionId != null) {
                throw new AppException(ErrorCodeEnum.NULL_POINTER);
            }

            UUID uuid = UUID.randomUUID(); // Tạo UUID
            String password = encodeBase62(uuid.toString());

//            Agent agent = Agent.builder()
//                    .fullname(request.getFullname())
//                    .isMain(false)
//                    .alias(request.getAlias() == null ? null : request.getAlias())
//                    .companyId(request.getCompanyId())
//                    .email(request.getEmail())
//                    .isActive(true)
//                    .password(passwordEncoder.encode(password))
//                    .build();
            Agent agent = new Agent();
            agent.setFullname(request.getFullname());
            agent.setMain(false);
            agent.setAlias(request.getAlias() == null ? null : request.getAlias());
            agent.setCompanyId(request.getCompanyId());
            agent.setEmail(request.getEmail());
            agent.setActive(true);
            agent.setPassword(passwordEncoder.encode(password));

            agentRepository.save(agent);

        }catch (AppException e){
            throw e;
        } catch (DataIntegrityViolationException e) {
            // Ghi log rõ ràng thông tin entity gặp lỗi
            log.error("Duplicate key error: Entity type: {}, Entity: {}, Message: {}",
                    e.getMessage(), e);
            throw new AppException(ErrorCodeEnum.DUPLICATE_DATA, "Entity already exists");
        } catch (Exception e) {
        log.error("Error updating entity: {}", e.getMessage(), e);
        throw new AppException(ErrorCodeEnum.INVALID_KEY, "Internal server error");
    }
        return null;
    }





    public static String encodeBase62(String uuid) {
        String base62Characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder base62String = new StringBuilder();
        long value = uuid.hashCode(); // Dùng hashCode của UUID để tạo chuỗi ngắn hơn
        while (value > 0) {
            base62String.insert(0, base62Characters.charAt((int)(value % 62)));
            value /= 62;
        }
        return base62String.toString();
    }


//    private void validateEmailNotExist(String email) {
//        if (agentRepository.existsByEmail(email)) {
//            throw new AppException(ErrorCodeEnum.USER_EXISTED, "Email already exists");
//        }
//    }

}