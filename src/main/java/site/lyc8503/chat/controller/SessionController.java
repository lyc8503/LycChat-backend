package site.lyc8503.chat.controller;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import site.lyc8503.chat.exception.InvalidCredentialException;
import site.lyc8503.chat.pojo.vo.CommonResponse;
import site.lyc8503.chat.pojo.vo.session.PostSessionRequest;
import site.lyc8503.chat.pojo.vo.session.PostSessionResponse;
import site.lyc8503.chat.service.UserService;

import javax.validation.Valid;


@Slf4j
@RestController
@Api(tags = "会话管理")
@RequiredArgsConstructor
public class SessionController {

    // As of Spring 4.3, if a class defines only one single constructor (using lombok in this case),
    // Spring will understand to use that constructor without needing to add @Autowired or any other annotations.
    private final UserService userService;

    @PostMapping(value = "/session")
    @Operation(summary = "登录")
    public CommonResponse<PostSessionResponse> postSession(@Valid @RequestBody PostSessionRequest request) throws InvalidCredentialException {

        if (userService.login(request.getUsername(), request.getPassword())) {
            StpUtil.login(request.getUsername());

            PostSessionResponse response = PostSessionResponse.builder()
                    .token(StpUtil.getTokenValue())
                    .expires(StpUtil.getTokenTimeout())
                    .build();

            return CommonResponse.success(response);
        } else {
            throw new InvalidCredentialException("用户名或密码错误");
        }

    }

    @DeleteMapping("/session")
    @Operation(summary = "登出")
    public CommonResponse<Object> deleteSession() {
        StpUtil.checkLogin();
        StpUtil.logout();

        return CommonResponse.success(null);
    }

    @GetMapping("/session")
    @Operation(summary = "获取会话状态")
    public CommonResponse<Object> getSession() {

        StpUtil.getLoginId();

        return CommonResponse.success(null);
    }

}
