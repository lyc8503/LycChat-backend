package site.lyc8503.chat.exception;

import lombok.Getter;

public abstract class BaseException extends Exception {
    @Getter
    private final int code;
    @Getter
    private final String message;
    @Getter
    private final int httpCode;

    public BaseException(int code, String message) {
        this.code = code;
        this.message = message;
        this.httpCode = 200;
    }

    public BaseException(int code, String message, int httpCode) {
        this.code = code;
        this.message = message;
        this.httpCode = httpCode;
    }

}
