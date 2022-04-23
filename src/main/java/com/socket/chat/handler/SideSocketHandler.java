package com.socket.chat.handler;

import com.socket.chat.dto.CoinResponseDTO;
import com.socket.chat.service.CoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
public class SideSocketHandler extends TextWebSocketHandler {

    private final CoinService coinService;
    private Set<WebSocketSession> sessionSet = new HashSet<WebSocketSession>();


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionSet.add(session);
        log.info("{} 클라이언트 접속 ", session);
        log.info("전체 클라이언트 접속자수 {}", sessionSet);
        TextMessage str = new TextMessage("반갑습니다티비1");
        for (WebSocketSession s : sessionSet) {
            s.sendMessage(new TextMessage(str.toString()));
        }
        sessionSet.remove(session);
    }
}
