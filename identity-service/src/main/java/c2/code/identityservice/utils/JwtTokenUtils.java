package c2.code.identityservice.utils;

import c2.code.identityservice.enums.ErrorCodeEnum;
import c2.code.identityservice.exceptions.AppException;
import c2.code.identityservice.models.auth.CustomUserDetails;
import c2.code.identityservice.models.dto.AuthorizeDTO;
import c2.code.identityservice.models.dto.PermissionDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Log4j2
@Component
@RequiredArgsConstructor
public class JwtTokenUtils {
    @Value("${jwt.expiration}")
    private int expiration; //save to an environment variable

    @Value("${jwt.secretKey}")
    private String secretKey;

    private final ObjectMapper objectMapper;


    public Mono<String> generateToken(Authentication authentication) {
        return Mono.fromCallable(() -> {
            // Lấy thông tin từ đối tượng người dùng đã được xác thực
            CustomUserDetails agent = (CustomUserDetails) authentication.getPrincipal();
            AuthorizeDTO authorize = agent.getCustomAuthorize();
            // Tạo claims chứa các thông tin bạn muốn lưu trữ trong token
            Map<String, Object> claims = new HashMap<>();
            claims.put("username", agent.getUsername());
            claims.put("password", agent.getPassword());
            claims.put("author", authorize);



            // Tạo token với các claims và thông tin cần thiết
            return Jwts.builder()
                    .setClaims(claims) // Thêm claims vào token

                    .setSubject(agent.getUsername()) // Đặt chủ đề (thông thường là email hoặc tên đăng nhập)
                    .setIssuedAt(new Date(System.currentTimeMillis())) // Đặt thời gian phát hành token
                    .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L)) // Đặt thời gian hết hạn token
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256) // Ký token với secret key và thuật toán HS256
                    .compact(); // Hoàn thiện quá trình tạo token
        }).onErrorMap(e -> new AppException(ErrorCodeEnum.ERROR_GENERATE_TOKEN, "Lỗi tạo token"));
    }


    private Key getSignInKey() {
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }

    private String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[32]; // 256-bit key
        random.nextBytes(keyBytes);
        return Encoders.BASE64.encode(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> Mono<T> extractClaim(String token, Function<Claims, T> claimsResolver) {
        return Mono.fromCallable(() -> claimsResolver.apply(this.extractAllClaims(token)));
    }

    //check expiration
    public Mono<Boolean> isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration)
                .map(expirationDate -> expirationDate.before(new Date()));
    }

    public Mono<String> extractPhoneNumber(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Mono<String> extractByEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Mono<Boolean> validateToken(String token, UserDetails userDetails) {
        return extractByEmail(token)
                .map(username -> username.equals(userDetails.getUsername()))
                .zipWith(isTokenExpired(token), (isUsernameValid, isExpired) -> isUsernameValid && !isExpired);
    }

    public Mono<AuthorizeDTO> extractAuthorize(String token) {
        return extractClaim(token, claims -> {
            // Trích xuất claim "author" từ token
            Map<String, Object> authorMap = claims.get("author", Map.class);
            // Ánh xạ các giá trị trong claim sang đối tượng AuthorizeDTO
            if (authorMap != null) {
                return AuthorizeDTO.builder()
                        .permissonDTO((PermissionDTO) authorMap.get("permissionDTO"))
                        .build();
            }
            return null;
        });
    }


    public Mono<Jws<Claims>> verifyToken(String token) {
        try {
            // Xác minh chữ ký của token và phân tích các claims
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(secretKey) // Dùng secretKey để xác minh chữ ký của token
                    .build()
                    .parseClaimsJws(token); // Phân tích JWT và xác minh chữ ký

            // Kiểm tra thời gian hết hạn của token
            if (claimsJws.getBody().getExpiration().before(new Date())) {
                throw new AppException(ErrorCodeEnum.UNAUTHENTICATED, "Token expired.");
            }

            // Nếu chữ ký hợp lệ và token không hết hạn, trả về claims
            return Mono.just(claimsJws);

        } catch (JwtException | IllegalArgumentException e) {
            // Xử lý ngoại lệ nếu token không hợp lệ (chữ ký sai, hết hạn, v.v.)
            throw new AppException(ErrorCodeEnum.UNAUTHENTICATED, "Invalid token.");
        }
    }

//    public Mono<AuthorizeDTO> extractAuthorize(String token) {
//        return extractClaim(token, claims -> {
//            // Lấy dữ liệu từ claim "author"
//            Map<String, Object> authorMap = claims.get("author", Map.class);
//            // Ánh xạ từ Map sang AuthorizeDTO bằng ObjectMapper
//            if (authorMap != null) {
//                return objectMapper.convertValue(authorMap, AuthorizeDTO.class);
//            }
//            return null;
//        });
//    }


}
