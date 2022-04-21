package com.socket.chat.handler;

import com.socket.chat.dto.CoinResponseDTO;
import com.socket.chat.service.CoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class SocketHandler extends TextWebSocketHandler implements InitializingBean { // implements InitializingBean
    private Set<WebSocketSession> sessionSet = new HashSet<WebSocketSession>();
//    private Map<String, String> coinData2 = new HashMap<>();
    private final CoinService coinService;

    List<Integer> list;

    @PostConstruct
    public void test(){
        list = new ArrayList<>();
        log.info("test");
    }

    CoinResponseDTO data = new CoinResponseDTO("2", "2", "hio");
    CoinResponseDTO coinData = data.toEntity();
    String json;


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
            s.sendMessage(new TextMessage(str.toString()));
        }
    }


    // 클라이언트가 연결을 끊었 을 때 실행
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("{} 클라이언트 접속 헤제", session);
        sessionSet.remove(session);
    }

    // 연결된 Client에 메시지 Broadcast 목적
    public void broadcast (String message) {
        for (WebSocketSession session : this.sessionSet) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(message.toString()));
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
        int i=0;
        list.add(50);
        log.info("{} 엔티티 데이터", coinData.getPrice());
        json =  "{\"userId\":\"sim\", "
                + "\"userPw\":\"simpw\","
                + "\"userInfo\":{"
                + "\"age\":\"50\""
                + "\"price\":";
        List<Integer> coins = coinService.listAdd();
        for (Integer coin: coins) {
            json+="["+"\"coin\":"+coin+"],";
        }
        json.substring(0,json.length()-1);
                json+= "}"
                + "}";


        log.info("hi2 {}",json);
        Thread thread = new Thread(){

            @Override
            public void run() {
                while (true){
                    try {
                        json =  "{\"userId\":\"sim\", "
                                + "\"userPw\":\"simpw\","
                                + "\"userInfo\":{"
                                + "\"age\":\"50\""
                                + "\"price\":"+ coinService.listAdd()
                        + "}"
                                + "}";
                        log.info("ttttt {}",json);
                        broadcast("hi" + json);
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
