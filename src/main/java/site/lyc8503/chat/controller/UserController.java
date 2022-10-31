package site.lyc8503.chat.controller;


import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import site.lyc8503.chat.pojo.vo.CommonResponse;
import site.lyc8503.chat.pojo.vo.user.PostUserRequest;
import site.lyc8503.chat.service.UserService;

import javax.validation.Valid;

@Slf4j
@RestController
@Api(tags = "用户")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    @Operation(summary = "创建用户(注册)")
    public CommonResponse<?> postUser(@Valid @RequestBody PostUserRequest request) {

        userService.register(request.getUsername(), request.getPassword(), request.getNickname(), request.getEmail());

        return CommonResponse.success(201);
    }

}
