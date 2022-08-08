package site.lyc8503.chat.controller;


import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import site.lyc8503.chat.pojo.vo.CommonResponse;
import site.lyc8503.chat.service.UserService;

@Slf4j
@RestController
@Api(tags = "用户")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

}
