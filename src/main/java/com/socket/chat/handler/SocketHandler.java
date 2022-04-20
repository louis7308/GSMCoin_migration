package com.socket.chat.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class SocketHandler extends TextWebSocketHandler implements InitializingBean {
    private Set<WebSocketSession> sessionSet = new HashSet<WebSocketSession>();

    // 클라이언트가 서버로 메세지를 전송했을 때 실행
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload: {}", payload);
        for (WebSocketSession s : sessionSet) {
            s.sendMessage(message);
        }
    }

    // 클라이언트가 서버로 연결된 이후에 실행
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionSet.add(session);
        log.info("{} 클라이언트 접속 ", session);
        log.info("전체 클라이언트 접속자수 {}", sessionSet);
        TextMessage str = new TextMessage("반갑습니다티비");
        for (WebSocketSession s : sessionSet) {
            s.sendMessage(str);
        }
    }

    // 클라이언트가 연결을 끊었 을 때 실행
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("{} 클라이언트 접속 헤제", session);
        sessionSet.remove(session);
    }

    // 연결된 Client에 메시지 Broadcast 목적
    public void broadcast(String message) {
        for (WebSocketSession session : this.sessionSet) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(message));
                } catch (Exception ignored) {
                    this.log.error("fail to send message!", ignored);
                    sessionSet.remove(session);
                    try {
                        session.close();
                    } catch (IOException e1) {
                    }
                }
            }
        }
    }

    // 클라이언트에게 주기적 으로 broadcast를 3초마다 보낸다
    @Override
    public void afterPropertiesSet() throws Exception {
        Thread thread = new Thread(){
            int i=0;
            @Override
            public void run() {
                while (true){
                    try {
                        broadcast("send message index "+i++);
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        };
        thread.start();
    }

}
