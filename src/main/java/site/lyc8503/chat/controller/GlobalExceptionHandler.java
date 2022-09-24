package site.lyc8503.chat.controller;

import cn.dev33.satoken.exception.NotLoginException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.lyc8503.chat.exception.BizException;
import site.lyc8503.chat.exception.ErrorType;
import site.lyc8503.chat.pojo.vo.CommonResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * global catch
     */

    @ExceptionHandler(BizException.class)
    public CommonResponse<?> handleBaseException(BizException e) {
        log.error("Biz Exception", e);
        return CommonResponse.error(e);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public CommonResponse<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("Http Message Not Readable Exception", e);
        return CommonResponse.error(ErrorType.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Invalid Argument", e);
        return CommonResponse.error(ErrorType.ILLEGAL_ARGUMENTS, e.getAllErrors().get(0).getDefaultMessage());
//        return CommonResponse.error(ResponseType.ILLEGAL_ARGUMENTS, e.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining("/")));
    }

    @ExceptionHandler(NotLoginException.class)
    public CommonResponse<?> handleAuthorizeException(NotLoginException e) {
        log.error("Not Login Exception", e);
        return CommonResponse.error(ErrorType.NOT_LOGIN, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public CommonResponse<?> handleDefaultException(Exception e) {
        log.error("Global catch", e);
        return CommonResponse.error(ErrorType.UNKNOWN_ERROR);
    }
}
