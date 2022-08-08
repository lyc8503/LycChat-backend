package site.lyc8503.chat.controller;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import site.lyc8503.chat.domain.vo.CommonResponse;
import site.lyc8503.chat.domain.vo.session.PostSessionRequest;
import site.lyc8503.chat.domain.vo.session.PostSessionResponse;
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
    public CommonResponse<PostSessionResponse> postSession(@Valid @RequestBody PostSessionRequest request) {

        PostSessionResponse response = PostSessionResponse.builder()
                .token("123")
                .expires(123)
                .build();

        return CommonResponse.success(response);
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
