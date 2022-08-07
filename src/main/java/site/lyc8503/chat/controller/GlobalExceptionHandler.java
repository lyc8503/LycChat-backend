package site.lyc8503.chat.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.lyc8503.chat.domain.vo.CommonResponse;
import site.lyc8503.chat.exception.BaseException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<CommonResponse<?>> handleBaseException(BaseException e) {
        log.error("Base Exception", e);
        return ResponseEntity.status(e.getHttpCode()).body(CommonResponse.error(e.getCode(), e.getMessage()));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public CommonResponse<?> defaultExceptionHandler(Exception e) {
        log.error("Global catch", e);
        return CommonResponse.error(500, "unknown internal server error");
    }

}
