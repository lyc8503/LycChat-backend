package site.lyc8503.chat.pojo.vo.session;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "查询登录状态响应")
public class GetSessionResponse {
    @Schema(description = "登录是否有效", required = true)
    private boolean isLogin;
    @Schema(description = "当前用户id")
    private String username;
}
