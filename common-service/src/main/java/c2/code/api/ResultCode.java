package c2.code.api;

import lombok.Getter;

@Getter
public enum ResultCode {

    SUCCESS(200, "success"),
    INTERNAL_ERROR(500, "internal error"),
    UNAUTHORIZED(401, "unauthorized"),
    FORBIDDEN(403, "forbidden"),
    // 100-199 user error code
    USERNAME_EXISTS(100, "Username đã tồn tại"),
    EMAIL_EXISTS(101, "Email đã tồn tại"),
    INVALID_USERNAME_PASSWORD(102, "Tên đăng nhập hoặc mật khẩu không đúng"),

    USER_NOT_FOUND(103, "User không tồn tại"),
    INVALID_TOKEN(104, "Token không hợp lệ"),

    // 200-299 request error code
    INVALID_FORMAT_REQUEST(105, "Yêu cầu không hợp lệ"),
    INVALID_MID(105, "Mid không hợp lệ"),
    USER_MUST_LOGIN(106, "User phải đăng nhập trước khi gửi yêu cầu khác"),
    INVALID_USER(107, "User không hợp lệ");


    private long code;
    private String message;

    ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }
}
