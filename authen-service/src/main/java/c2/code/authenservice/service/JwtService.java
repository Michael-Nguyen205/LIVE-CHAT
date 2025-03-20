package c2.code.authenservice.service;

import c2.code.api.ResultCode;
import c2.code.exception.ApiException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class JwtService {

    @Value("${jwt.secret:be2b489e87d85cf72081f644e969f68fc018a89dce8cace173374dbe1c77a3a6}")
    private String jwtSecretKey;
    public static final long JWT_EXPIRE = 5 * 24 * 3600 * 1000;

    public String generateJwtToken(long userId) {
        return Jwts.builder()
                .setSubject(userId + "")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRE))
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .compact();
    }

    public boolean verifyToken(String userId, String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token).getBody();
            if (!claims.getSubject().equals(userId)) {
                log.error("Invalid user id in token");
                return true;
            }
            return true;
        } catch (Exception ex) {
            log.error("Error verify token", ex);
            return false;
        }
    }
}
