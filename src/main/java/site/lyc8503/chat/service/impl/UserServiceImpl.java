package site.lyc8503.chat.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.lyc8503.chat.dao.UserDao;
import site.lyc8503.chat.exception.BizException;
import site.lyc8503.chat.exception.ErrorType;
import site.lyc8503.chat.pojo.entity.UserEntity;
import site.lyc8503.chat.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Override
    public void login(String username, String password) {
        UserEntity userEntity = userDao.findByUsername(username);
        if (userEntity == null) {
            throw new BizException(ErrorType.INVALID_CREDENTIAL);
        }

        if (!BCrypt.checkpw(password, userEntity.passwordHash)) {
            throw new BizException(ErrorType.INVALID_CREDENTIAL);
        }
    }

    @Override
    public void register(String username, String password, String nickname, String email) {

        // user exists
        if (userDao.findByUsername(username) != null) {
            throw new BizException(ErrorType.USERNAME_EXISTS);
        }

        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .passwordHash(BCrypt.hashpw(password))
                .email(email)
                .nickname(nickname)
                .build();

        userDao.save(userEntity);
    }
}
