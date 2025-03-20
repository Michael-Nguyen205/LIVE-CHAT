package c2.code.exception;

import c2.code.api.ResultCode;
import lombok.Data;

@Data
public class ApiException extends RuntimeException{

    private ResultCode errorCode;

    public ApiException(ResultCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public static ApiException failed(ResultCode resultCode) {
        return new ApiException(resultCode);
    }
}
