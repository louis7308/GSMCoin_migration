package com.socket.chat.service;

import com.socket.chat.dto.CoinResponseDTO;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CoinService {

    private final
    List<Integer> list = new ArrayList<>();

    public List<Integer> listAdd() {
        list.add(50);
        System.out.println("list.toString() = " + list.toString());
        return list;
    }

    public String AllCoin() {
        String json =  "{\"userId\":\"sim\", "
                + "\"userPw\":\"simpw\","
                + "\"userInfo\":{"
                + "\"age\":\"50\","
                + "\"price\":"+ this.listAdd()
                + "}"
                + "}";
        return json;
    }
}
