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
    Map<String, String> data= new HashMap<>();
    List<Integer> list = new ArrayList<>();

    public List<Integer> listAdd() {
        list.add(50);
        System.out.println("list.toString() = " + list.toString());
        return list;
    }

    public Map<String, String> getPrice(){
        int i=0;
//        CoinResponseDTO coinResponseDTO = new CoinResponseDTO(1, "test@naver.com");
//        data.put("price", coinResponseDTO);
        data.put("price", "1");
        return data;
    }
}
