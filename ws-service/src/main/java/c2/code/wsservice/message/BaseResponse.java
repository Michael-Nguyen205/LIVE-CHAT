package c2.code.wsservice.message;

import c2.code.api.CommonResult;
import c2.code.api.ResultCode;
import lombok.Data;

@Data
public class BaseResponse {

    String mid;
    long timestamp;
    long code;
    String serverId;
    String description;
    String clientRequestId;
    String requestId;

    public void init(BaseMessage message, CommonResult result) {
        this.mid = message.getMid();
        this.serverId = message.getServerId();
        this.clientRequestId = message.getClientRequestId();
        this.timestamp = System.currentTimeMillis();
        this.clientRequestId = message.getClientRequestId();
        this.code = result.getCode();
        this.description = result.getMessage();
    }

    public static BaseResponse fail(ResultCode resultCode) {
        BaseResponse response = new BaseResponse();
        response.setTimestamp(System.currentTimeMillis());
        response.setCode(resultCode.getCode());
        response.setDescription(resultCode.getMessage());
        return response;
    }

    public void setResultCode(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.description = resultCode.getMessage();
    }

}
