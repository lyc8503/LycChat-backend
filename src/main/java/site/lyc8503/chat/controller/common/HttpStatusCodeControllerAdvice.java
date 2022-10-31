package site.lyc8503.chat.controller.common;


import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import site.lyc8503.chat.pojo.vo.CommonResponse;


@ControllerAdvice
public class HttpStatusCodeControllerAdvice implements ResponseBodyAdvice<CommonResponse<?>> {
    /**
     * Used to set http status code
     */

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return returnType.getParameterType().isAssignableFrom(CommonResponse.class);
    }

    @Override
    public CommonResponse<?> beforeBodyWrite(CommonResponse<?> body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body != null) {
            response.setStatusCode(HttpStatus.valueOf(body.getHttpCode()));
        }
        return body;
    }
}
