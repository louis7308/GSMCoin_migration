package com.socket.chat.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class ChatController {
    @GetMapping("/chat")
    public String chat(){
        log.info("@ChatController");
        return "socket/chat";
    }
}
