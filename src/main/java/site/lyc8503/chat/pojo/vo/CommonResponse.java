package site.lyc8503.chat.pojo.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import site.lyc8503.chat.exception.BizException;
import site.lyc8503.chat.exception.ErrorType;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ApiModel(value = "通用响应")
public class CommonResponse<T> {
    @Schema(description = "响应码", required = true)
    private int code;
    @Schema(description = "响应消息", required = true)
    private String msg;
    @Schema(description = "响应数据")
    private T data;

    @Hidden
    @JsonIgnore
    // used to define http status code
    private int httpCode;

    public static <T> CommonResponse<T> success() {
        return success(null);
    }

    public static <T> CommonResponse<T> success(T data) {
        return success(data, 200);
    }

    public static <T> CommonResponse<T> success(T data, int httpCode) {
        return new CommonResponse<>(0, "success", data, httpCode);
    }

    public static <T> CommonResponse<T> error(ErrorType type) {
        return new CommonResponse<>(type.getCode(), type.getMessage(), null, type.getHttpCode());
    }

    public static <T> CommonResponse<T> error(ErrorType type, String msg) {
        return new CommonResponse<>(type.getCode(), msg, null, type.getHttpCode());
    }

    public static <T> CommonResponse<T> error(BizException exception) {
        return new CommonResponse<>(exception.getCode(), exception.getMessage(), null, exception.getHttpCode());
    }


}
