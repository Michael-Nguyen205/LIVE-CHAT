package c2.code.authenservice.exceptions;

import c2.code.api.CommonResult;
import jakarta.annotation.PostConstruct;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.TransactionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import spring.boot.webcococo.enums.ErrorCodeEnum;
import spring.boot.webcococo.models.response.ApiResponse;
import spring.boot.webcococo.utils.LocalizationUtils;

import java.sql.SQLException;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final LocalizationUtils localizationUtils;

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<CommonResult> handleRuntimeException(RuntimeException exception) {
        log.error("An error occurred: ", exception);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(ErrorCodeEnum.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCodeEnum.UNCATEGORIZED_EXCEPTION.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = SQLException.class)
    public ResponseEntity<CommonResult> handleSQLException(SQLException exception) {
        log.error("An error occurred: ", exception);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(ErrorCodeEnum.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCodeEnum.UNCATEGORIZED_EXCEPTION.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = TransactionException.class)
    public ResponseEntity<CommonResult> handleTransactionException(TransactionException exception) {
        log.error("An error occurred: ", exception);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(ErrorCodeEnum.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCodeEnum.UNCATEGORIZED_EXCEPTION.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<CommonResult<?>> handleAppException(AppException exception) {
        ApiResponse<Object> apiResponse = new ApiResponse<>();
        ErrorCodeEnum ErrorCodeEnum = exception.getErrorCodeEnum();

        try {
            apiResponse.setMessage(localizationUtils.getLocalizedMessage(ErrorCodeEnum.getMessage()));
        } catch (NoSuchMessageException e) {
            apiResponse.setMessage("An error occurred.");
        }

        apiResponse.setCode(ErrorCodeEnum.getCode());
        apiResponse.setDetailMess(exception.getDetailMess());
        apiResponse.setResult(exception.getResponseData());

        return ResponseEntity.status(ErrorCodeEnum.getStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<CommonResult> handleAccessDeniedException(AccessDeniedException exception) {
        ErrorCodeEnum errorCode = ErrorCodeEnum.UNAUTHENTICATED;
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }




    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<ApiResponse> handleBadCredentialsException(BadCredentialsException exception) {
        ErrorCodeEnum errorCode = ErrorCodeEnum.UNAUTHENTICATED;
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }



    // kiểm tra lỗi ở đầu vào controller
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResult> handleValidation(MethodArgumentNotValidException exception) {
        log.error("MethodArgumentNotValidException");
        String enumKey = exception.getFieldError().getDefaultMessage();
        ErrorCodeEnum errorCode = null;

        try {
            errorCode = ErrorCodeEnum.valueOf(enumKey);
        } catch (IllegalArgumentException e) {
            errorCode = ErrorCodeEnum.INVALID_KEY;
        }

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }




    // kiểm tra lỗi ở tầng lưu vào db

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<CommonResult> handleConstraintViolation(ConstraintViolationException exception) {
        String errorMessage = exception.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage) // Lấy từng message
                .collect(Collectors.joining(", ")); // Gộp thành chuỗi
        ErrorCodeEnum errorCode = ErrorCodeEnum.INVALID_KEY;
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage("Validation error: " + errorMessage);
        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);

    }





    @PostConstruct
    public void init() {
        log.error("GlobalExceptionHandler is initialized");
    }
}
