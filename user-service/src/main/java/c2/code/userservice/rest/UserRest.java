package c2.code.userservice.rest;

import c2.code.api.CommonResult;
import c2.code.dto.request.CheckSendMessageRequest;
import c2.code.dto.response.CheckSendMessageResponse;
import c2.code.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserRest {

    @Autowired
    private UserService userService;

    @PostMapping("/can-send-message")
    public CommonResult<CheckSendMessageResponse> canSendMessageToFriend(@RequestBody CheckSendMessageRequest request) {
        return CommonResult.success(userService.checkCanSendMessage(request));
    }
}
