package site.lyc8503.chat.service;

import org.springframework.data.domain.Page;
import site.lyc8503.chat.pojo.dto.UserDTO;

public interface UserService {

    void login(UserDTO user);
    void register(UserDTO user);

    Page<UserDTO> searchUser(String query, int page, int size);

}
