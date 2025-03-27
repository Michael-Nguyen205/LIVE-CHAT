package c2.code.identityservice.service.impl;

import c2.code.identityservice.entity.sql.Token;
import c2.code.identityservice.enums.ErrorCodeEnum;
import c2.code.identityservice.exceptions.AppException;
import c2.code.identityservice.repository.AgentRepository;
import c2.code.identityservice.repository.TokenRepository;
import c2.code.identityservice.service.ITokenService;
import c2.code.identityservice.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements ITokenService {
//    private static final int MAX_TOKENS = 3;


    @Value("${jwt.max-token}")
    private int MAX_TOKENS; //save to an environment variable

    @Value("${jwt.expiration}")
    private int expiration; //save to an environment variable

    @Value("${jwt.expiration-refresh-token}")
    private int expirationRefreshToken;

    private final AgentRepository agentRepository;

    private final TokenRepository tokenRepository;

    private final JwtTokenUtils jwtTokenUtil;


    @Override
    public Mono<Token> addToken(UUID agentId, String token, boolean isMobileDevice) {
                    return tokenRepository.findByAgentId(agentId)
                            .collectList()
                            .flatMap(userTokens -> {
                                log.error("đang ở trong dâyyyy");
                                int tokenCount = userTokens.size();
                                if (tokenCount >= MAX_TOKENS) {
                                    log.error("MAX_TOKENS: {}",MAX_TOKENS);
                                    // Xóa token cũ
                                    return deleteOldToken(userTokens);
                                }
                                return Mono.empty(); // Không cần xóa token
                            })
                            .then(saveNewToken(agentId, token, isMobileDevice));

    }





    private Mono<Void> deleteOldToken(List<Token> userTokens) {

//        log.error("List<Tokens> : {} ",userTokens);
        Token tokenToDelete = userTokens.stream()
                .filter(userToken -> !userToken.isMobile())
                .findFirst()
                .orElse(userTokens.getFirst());
        return tokenRepository.delete(tokenToDelete).then(); // Xóa token
    }



    private Mono<Token> saveNewToken(UUID agentId, String token, boolean isMobileDevice) {
        log.error("đang lưu token");
        LocalDateTime expirationDateTime = LocalDateTime.now().plusSeconds(expiration);
        Token newToken = Token.builder()
                .id(UUID.randomUUID())
                .agentId(agentId)
                .token(token)
                .revoked(false)
                .expired(false)
                .expirationDate(expirationDateTime)
                .isMobile(isMobileDevice)
                .refreshToken(UUID.randomUUID().toString())
                .refreshExpirationDate(LocalDateTime.now().plusSeconds(expirationRefreshToken))
                .build();
        return tokenRepository.save(newToken)
                .onErrorResume(e ->{
//            throw new AppException(ErrorCode.DATABASE_SAVE_ERROR,"lưu token lỗi");
                    e.printStackTrace();
            return Mono.error(new AppException(ErrorCodeEnum.DATABASE_SAVE_ERROR, "Lưu token lỗi"));
        });
    }

}




//    @Override
//    public Token refreshToken(String refreshToken, Agent agent) throws Exception{
//        Token existingToken = tokenRepository.findByRefreshToken(refreshToken);
//        if(existingToken == null) {
//            throw new RuntimeException(" loi");
//        }
////
////
////        if(existingToken.getRefreshExpirationDate().compareTo(LocalDateTime.now()) < 0){
////            tokenRepository.delete(existingToken);
////            throw new AppException(ErrorCode.TOKEN_AUTHEN_ERRO);
////        }
////
////
////        String token = jwtTokenUtil.generateToken(user);
////        LocalDateTime expirationDateTime = LocalDateTime.now().plusSeconds(expiration);
////        existingToken.setExpirationDate(expirationDateTime);
////        existingToken.setToken(token);
////        existingToken.setRefreshToken(UUID.randomUUID().toString());
////        existingToken.setRefreshExpirationDate(LocalDateTime.now().plusSeconds(expirationRefreshToken));
//
//        return existingToken;
//    }


