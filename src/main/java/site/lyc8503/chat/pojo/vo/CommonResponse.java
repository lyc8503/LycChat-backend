package site.lyc8503.chat.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ApiModel(value = "通用响应")
public class CommonResponse<T> {
    @Schema(description = "响应码")
    private int code;
    @Schema(description = "响应消息")
    private String msg;
    @Schema(description = "响应数据")
    private T data;

    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<>(0, "success", data);
    }

    public static <T> CommonResponse<T> error(int code, String msg, T data) {
        return new CommonResponse<>(code, msg, data);
    }

    public static CommonResponse<?> error(int code, String msg) {
        return new CommonResponse<>(code, msg, null);
    }
}
