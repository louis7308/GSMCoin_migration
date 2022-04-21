package com.socket.chat.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CoinResponseDTO {

    private String price;
    private String sender;
    private String data;

    public void setSender(String sender) { this.sender = sender;}

    public CoinResponseDTO toEntity(){
        CoinResponseDTO board = CoinResponseDTO.builder()
                .price(price)
                .sender(sender)
                .data(data)
                .build();
        return board;
    }
}
