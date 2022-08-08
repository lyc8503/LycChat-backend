package site.lyc8503.chat.pojo.entity;

import lombok.Builder;

@Builder
public class UserEntity {

    public int id;
    public String nickname;
    public String username;
    public String email;
    public String passwordHash;

}
