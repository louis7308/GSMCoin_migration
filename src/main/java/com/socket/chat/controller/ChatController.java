package com.socket.chat.controller;

import com.socket.chat.dto.CoinResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
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

//    @MessageMapping("/coin")
////    @SendTo("/send/coin")
////    public CoinResponseDTO coinResponseDTO(){
//////        return new CoinResponseDTO(11, "ewewewe");
////
////    }
}
