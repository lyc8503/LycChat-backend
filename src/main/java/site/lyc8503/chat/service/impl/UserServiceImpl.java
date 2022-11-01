package site.lyc8503.chat.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import site.lyc8503.chat.dao.UserDao;
import site.lyc8503.chat.exception.BizException;
import site.lyc8503.chat.exception.ErrorType;
import site.lyc8503.chat.pojo.dto.UserDTO;
import site.lyc8503.chat.pojo.entity.UserEntity;
import site.lyc8503.chat.pojo.mapper.UserMapper;
import site.lyc8503.chat.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Override
    public void login(UserDTO userDTO) {
        UserEntity userEntity = userDao.findByUsername(userDTO.getUsername());
        if (userEntity == null) {
            throw new BizException(ErrorType.INVALID_CREDENTIAL);
        }

        if (!BCrypt.checkpw(userDTO.getPassword(), userEntity.passwordHash)) {
            throw new BizException(ErrorType.INVALID_CREDENTIAL);
        }
    }

    @Override
    public void register(UserDTO userDTO) {

        // user exists
        if (userDao.findByUsername(userDTO.getUsername()) != null) {
            throw new BizException(ErrorType.USERNAME_EXISTS);
        }

        UserEntity userEntity = UserMapper.INSTANCE.userDTOToUserEntity(userDTO);
        userDao.save(userEntity);
    }

    @Override
    public Page<UserDTO> searchUser(String query, int page, int size) {
        Page<UserEntity> userEntities = userDao.findAllByUsernameContaining(query, PageRequest.of(page, size));

        return userEntities.map(UserMapper.INSTANCE::userEntityToUserDTO);
    }
}
