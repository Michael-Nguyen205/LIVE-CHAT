package c2.code.authenservice.service.impl;

import c2.code.authenservice.entity.sql.Agent;
import c2.code.authenservice.enums.ErrorCodeEnum;
import c2.code.authenservice.exceptions.AppException;
import c2.code.authenservice.models.request.SignUpSubAgentRequest;
import c2.code.authenservice.repository.AgentRepository;
import c2.code.authenservice.service.IAgentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Log4j2
@RequiredArgsConstructor

@Service("agentServiceImpl")  // Đặt tên cho bean
public class AuthenServiceImpl implements IAgentService {

    private  final AgentRepository agentRepository;
    private final PasswordEncoder passwordEncoder;

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

            Agent agent = Agent.builder()
                    .fullname(request.getFullname())
                    .isMain(false)
                    .alias(request.getAlias() == null ? null : request.getAlias())
                    .companyId(request.getCompanyId())
                    .email(request.getEmail())
                    .isActive(true)
                    .password(passwordEncoder.encode(password))
                    .build();


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