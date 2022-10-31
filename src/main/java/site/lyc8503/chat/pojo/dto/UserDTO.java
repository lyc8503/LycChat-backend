package site.lyc8503.chat.pojo.dto;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDTO {
    private String username;
    private String password;
    private String nickname;
    private String email;
}
