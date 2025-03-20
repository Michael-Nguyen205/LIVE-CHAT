package c2.code.api;

import lombok.Data;

import java.util.Date;

@Data
public class CommonResult<T> {

    private long code;
    private String message;
    private T data;
    private Date timestamp;

    public CommonResult() {

    }

    protected CommonResult(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = new Date();
    }

    public boolean isSuccess() {
        return this.code == ResultCode.SUCCESS.getCode();
    }

    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    public static <T> CommonResult<T> success() {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }

    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), message, data);
    }

    public static <T> CommonResult<T> failed(ResultCode resultCode, T data) {
        return new CommonResult<T>(resultCode.getCode(), resultCode.getMessage(), data);
    }

    public static <T> CommonResult<T> failed(ResultCode resultCode) {
        return new CommonResult<T>(resultCode.getCode(), resultCode.getMessage(), null);
    }


    public static <T> CommonResult<T> failed(ResultCode resultCode, T data, String message) {
        return new CommonResult<T>(resultCode.getCode(), message, data);
    }

    public static <T> CommonResult<T> internalError(T data) {
        return new CommonResult<T>(ResultCode.INTERNAL_ERROR.getCode(), ResultCode.INTERNAL_ERROR.getMessage(), data);
    }

    public static <T> CommonResult<T> internalError(String message) {
        return new CommonResult<T>(ResultCode.INTERNAL_ERROR.getCode(), message, null);
    }

    public static <T> CommonResult<T> internalError(T data, String message) {
        return new CommonResult<T>(ResultCode.INTERNAL_ERROR.getCode(), message, data);
    }


}
