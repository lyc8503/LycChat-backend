package site.lyc8503.chat.service;

public interface UserService {

    void login(String username, String password);
    void register(String username, String password, String nickname, String email);

}
