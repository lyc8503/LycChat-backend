package site.lyc8503.chat.exception;

import lombok.Getter;

public enum ErrorType {

    // Common Error
    UNKNOWN_ERROR(100000, "未知错误", 500),
    BAD_REQUEST(100001, "无效请求", 400),
    ILLEGAL_ARGUMENTS(100002, "参数错误", 400),
    NOT_LOGIN(100003, "用户未登录", 401),

    // User Service Error
    INVALID_CREDENTIAL(200000, "用户名或密码错误", 401),
    USERNAME_EXISTS(200001, "用户名已存在", 409);

    @Getter
    private final int code;
    @Getter
    private final String message;
    @Getter
    private final int httpCode;

    ErrorType(int code, String message, int httpCode) {
        this.code = code;
        this.message = message;
        this.httpCode = httpCode;
    }

}
