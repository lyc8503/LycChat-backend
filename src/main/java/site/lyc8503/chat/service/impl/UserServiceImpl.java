package site.lyc8503.chat.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import site.lyc8503.chat.mapper.UserMapper;
import site.lyc8503.chat.pojo.entity.UserEntity;
import site.lyc8503.chat.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    public boolean login(String username, String password) {
        UserEntity userEntity = userMapper.getUserByUsername(username);
        if (userEntity == null) {
            return false;
        }

        return BCrypt.checkpw(password, userEntity.passwordHash);
    }

    @Override
    public boolean register(String username, String password, String nickname, String email) {

        // user exists
        if (userMapper.getUserByUsername(username) != null) {
            return false;
        }

        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .passwordHash(BCrypt.hashpw(password))
                .email(email)
                .nickname(nickname)
                .build();

        userMapper.insertUser(userEntity);
        return true;
    }
}
