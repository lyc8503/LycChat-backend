package site.lyc8503.chat.service;

public interface UserService {

    boolean login(String username, String password);
    boolean register(String username, String password, String nickname, String email);

}
