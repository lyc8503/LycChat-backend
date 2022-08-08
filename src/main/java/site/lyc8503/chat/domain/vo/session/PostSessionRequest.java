package site.lyc8503.chat.domain.vo.session;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Schema(description = "登录请求")
public class PostSessionRequest {

    @Schema(description = "用户名")
    @NotEmpty(message = "用户名不能为空")
    @Size(min = 4, max = 16, message = "用户名长度必须在 4-16 之间")
    @Pattern(regexp = "^[a-zA-Z\\d-_]*$", message = "用户名只能包含字母,数字,下划线和连字符")
    private String username;

    @Schema(description = "密码")
    @NotEmpty(message = "密码不能为空")
    @Size(min = 8, max = 32, message = "密码长度必须在 8-32 之间")
//    @Pattern(regexp = ".*[a-z]*.*", message = "密码必须包含小写字母")
//    @Pattern(regexp = ".*[A-Z]*.*", message = "密码必须包含大写字母")
//    @Pattern(regexp = ".*\\d*.*", message = "密码必须包含数字")
    private String password;
}
