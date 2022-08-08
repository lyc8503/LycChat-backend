package site.lyc8503.chat.service.impl;

import org.springframework.stereotype.Service;
import site.lyc8503.chat.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public boolean login(String username, String password) {
        return false;
    }

    @Override
    public boolean register(String username, String password, String nickname, String email) {
        return false;
    }
}
