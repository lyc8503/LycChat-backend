package site.lyc8503.chat.service;

import site.lyc8503.chat.pojo.dto.UserDTO;

public interface UserService {

    void login(UserDTO user);
    void register(UserDTO user);


}
