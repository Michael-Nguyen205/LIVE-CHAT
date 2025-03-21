package c2.code.authenservice.controller;


import c2.code.api.CommonResult;
import c2.code.dto.request.SignUpRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assignmentCondition")
public class DepartmentAgentAssignmentConditionController {


    @GetMapping("/{deparmentID}")
    public CommonResult signUp(@RequestBody SignUpRequest signUpRequest) {
        userService.register(signUpRequest);
        return CommonResult.success();
    }

}
