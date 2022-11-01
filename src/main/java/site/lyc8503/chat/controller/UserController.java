package site.lyc8503.chat.controller;


import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import site.lyc8503.chat.pojo.dto.UserDTO;
import site.lyc8503.chat.pojo.mapper.UserMapper;
import site.lyc8503.chat.pojo.vo.CommonResponse;
import site.lyc8503.chat.pojo.vo.user.PostUserRequest;
import site.lyc8503.chat.pojo.vo.user.UserVO;
import site.lyc8503.chat.service.UserService;

import javax.validation.Valid;
import java.util.List;

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
        userService.register(UserMapper.INSTANCE.postUserRequestToUserDTO(request));
        return CommonResponse.success(201);
    }

    @GetMapping("/users/search/{query:[a-z\\d-_]+}")
    @Operation(summary = "搜索用户")
    public CommonResponse<List<UserVO>> searchUser(@PathVariable String query) {

        Page<UserDTO> dtos = userService.searchUser(query, 0, 10);  // fixed size, just first 10 results

        return CommonResponse.success(dtos.stream().map(UserMapper.INSTANCE::userDTOtoUserVO).toList());
    }
}
