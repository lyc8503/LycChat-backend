package site.lyc8503.chat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableOpenApi
@SpringBootApplication
@MapperScan("site.lyc8503.chat.mapper")
public class ChatBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatBackendApplication.class, args);
    }

}
