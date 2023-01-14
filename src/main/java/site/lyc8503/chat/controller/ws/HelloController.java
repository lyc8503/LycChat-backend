package site.lyc8503.chat.controller.ws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class HelloController {

    @MessageMapping("/hello")
    @SendTo("/topic/hello")
    public String greeting(String msg) {
        log.debug("Received message: {}", msg);
        return "Hello from websocket!";
    }

}
