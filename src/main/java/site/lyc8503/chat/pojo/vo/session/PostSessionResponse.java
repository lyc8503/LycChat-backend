package site.lyc8503.chat.pojo.vo.session;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "登录请求响应")
public class PostSessionResponse {

    @Schema(description = "token", required = true)
    private String token;
    @Schema(description = "token有效期", required = true)
    private long expires;


}
