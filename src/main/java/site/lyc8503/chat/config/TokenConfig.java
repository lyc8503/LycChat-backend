package site.lyc8503.chat.config;

import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class TokenConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaRouteInterceptor((req, res, handler) -> {
                    // Not match session & register requests
                    SaRouter.match("/session").stop();
                    SaRouter.match(SaHttpMethod.POST).match("/users").stop();

                    SaRouter.match("/**")
                            .notMatch("/swagger-ui/**")
                            .notMatch("/v3/api-docs/**")
                            .notMatch("/swagger-resources/**")
                            .check(r -> StpUtil.checkLogin());
                }))
                .addPathPatterns("/**");
    }
}
