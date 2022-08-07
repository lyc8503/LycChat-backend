package site.lyc8503.chat.controller;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.lyc8503.chat.domain.vo.CommonResponse;
import site.lyc8503.chat.domain.vo.session.PostSessionRequest;
import site.lyc8503.chat.domain.vo.session.PostSessionResponse;
import site.lyc8503.chat.service.UserService;

@RestController
@Api(tags = "会话管理")
public class SessionController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/session", consumes = "application/json")
    @Operation(summary = "登录")
    public CommonResponse<PostSessionResponse> postSession(@RequestBody PostSessionRequest request) {


        return CommonResponse.success(new PostSessionResponse());
    }

    @DeleteMapping("/session")
    public CommonResponse<Object> deleteSession() {
        return CommonResponse.success(null);
    }

    @GetMapping("/session")
    @Operation(summary = "获取会话状态")
    public CommonResponse<Object> getSession() {
        return CommonResponse.success(null);
    }

}
