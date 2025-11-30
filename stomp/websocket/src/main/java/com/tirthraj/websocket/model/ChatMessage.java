package com.tirthraj.websocket.model;

import lombok.Data;

@Data
public class ChatMessage {
    private String content;
    private String sender;
}
