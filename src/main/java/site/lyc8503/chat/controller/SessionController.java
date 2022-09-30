package site.lyc8503.chat.controller;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import site.lyc8503.chat.pojo.vo.CommonResponse;
import site.lyc8503.chat.pojo.vo.session.GetSessionResponse;
import site.lyc8503.chat.pojo.vo.session.PostSessionRequest;
import site.lyc8503.chat.pojo.vo.session.PostSessionResponse;
import site.lyc8503.chat.service.UserService;

import javax.validation.Valid;


@Slf4j
@RestController
@Api(tags = "会话管理")
@RequiredArgsConstructor
@CrossOrigin
public class SessionController {

    // As of Spring 4.3, if a class defines only one single constructor (using lombok in this case),
    // Spring will understand to use that constructor without needing to add @Autowired or any other annotations.
    private final UserService userService;

    @PostMapping(value = "/session")
    @Operation(summary = "登录")
    public CommonResponse<PostSessionResponse> postSession(@Valid @RequestBody PostSessionRequest request) {

        // throws Exception if failed
        userService.login(request.getUsername(), request.getPassword());

        StpUtil.login(request.getUsername());

        PostSessionResponse response = PostSessionResponse.builder()
                .token(StpUtil.getTokenValue())
                .expires(StpUtil.getTokenTimeout())
                .build();

        return CommonResponse.success(response, 201);
    }

    @DeleteMapping("/session")
    @Operation(summary = "登出")
    public CommonResponse<?> deleteSession() {
        StpUtil.checkLogin();
        StpUtil.logout();

        return CommonResponse.success();
    }

    @GetMapping("/session")
    @Operation(summary = "获取会话状态")
    public CommonResponse<GetSessionResponse> getSession() {
        try {
            String loginId = (String) StpUtil.getLoginId();
            return CommonResponse.success(GetSessionResponse.builder()
                    .isLogin(true)
                    .username(loginId)
                    .build());
        } catch (NotLoginException e) {
            return CommonResponse.success(GetSessionResponse.builder()
                    .isLogin(false)
                    .username(null)
                    .build());
        }
    }

}
