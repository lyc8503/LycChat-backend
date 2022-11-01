package site.lyc8503.chat.controller.common;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/")
    public String hello() {
        return "Hello, World! From LycChat backend";
    }
}
