package site.lyc8503.chat.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    public int id;
    public String nickname;
    public String username;
    public String email;
    public String passwordHash;

}
