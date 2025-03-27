//package c2.code.authenservice.service;
//
//import c2.code.api.ResultCode;
//import c2.code.authenservice.dao.UserRedisDao;
//import c2.code.authenservice.entity.UserEntity;
//import c2.code.authenservice.repository.UserRepository;
//import c2.code.authenservice.utils.IdGenerator;
//import c2.code.dto.UserCache;
//import c2.code.dto.request.SignUpRequest;
//import c2.code.exception.ApiException;
//import lombok.extern.slf4j.Slf4j;
//import org.mindrot.jbcrypt.BCrypt;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//
//@Service
//@Slf4j
//public class UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private UserRedisDao userRedisDao;
//
//    @Autowired
//    private IdGenerator idGenerator;
//
//    public void register(SignUpRequest signUpRequest) {
//        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
//            log.error("Username exists {}", signUpRequest.getUsername());
//            throw ApiException.failed(ResultCode.USERNAME_EXISTS);
//        }
//
//        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
//            log.error("Email exists {}", signUpRequest.getEmail());
//            throw ApiException.failed(ResultCode.EMAIL_EXISTS);
//        }
//
//        String userId = idGenerator.nextId() + "";
//        log.info("User id: {}", userId);
//        UserCache userCache = new UserCache();
//        BeanUtils.copyProperties(signUpRequest, userCache);
//        userRedisDao.putUser(userId, userCache);
//
//        UserEntity userEntity = new UserEntity();
//        BeanUtils.copyProperties(signUpRequest, userEntity);
//        userEntity.setPassword(BCrypt.hashpw(signUpRequest.getPassword(), BCrypt.gensalt()));
//        userEntity.setCreatedAt(new Date());
//        userEntity.setId(Long.parseLong(userId));
//
//        log.info("Put user to db {}", userId);
//        userRepository.save(userEntity);
//
//        log.info("Register new user successful {}", signUpRequest.getUsername());
//    }
//}
