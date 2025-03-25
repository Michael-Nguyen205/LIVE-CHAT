package c2.code.wsservice.exceptions;

import c2.code.wsservice.enums.ErrorCodeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Log4j2
@Getter
@Setter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AppException extends RuntimeException {

    private ErrorCodeEnum errorCodeEnum;
    private Object data;
    private Object responseData;
    private String detailMess;
//    private String systemErrorMess;


    // Constructor có tham số dữ liệu
    public AppException(ErrorCodeEnum errorCode, String mess) {
//        super(mess);
        log.error("mess",mess);
        this.errorCodeEnum = errorCode;
        this.detailMess = mess;


//        this.systemErrorMess = mess;
    }

    // Constructor không có tham số dữ liệu
    public AppException(ErrorCodeEnum errorCode) {
//        super(errorCode.getMessage());
        this.errorCodeEnum = errorCode;
    }



    public AppException(ErrorCodeEnum errorCode,Object object) {
        super(errorCode.getMessage());
        this.errorCodeEnum = errorCode;
        this.responseData = object;
    }



}
