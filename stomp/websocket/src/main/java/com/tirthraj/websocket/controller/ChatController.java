package com.tirthraj.websocket.controller;

import com.tirthraj.websocket.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    // 1. Receives messages from /app/chat
    @MessageMapping("/chat")
    // 2. Broadcasts the return value to all subscribers of /topic/messages
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(ChatMessage message) {
        // You can save to DB here if needed
        return message;
    }

}
