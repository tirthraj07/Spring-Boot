package com.tirthraj.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 3. Register the "/ws" endpoint, enabling the SockJS fallback options so that alternate transports can be used if WebSocket is not available.
        // The SockJS client will attempt to connect to "http://localhost:8080/ws"
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")              // Allow connections from React (running on different port)
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 1. Enable a simple memory-based message broker to carry messages back to the client on destinations prefixed with "/topic"
        // /topic/ → paths where server broadcasts to clients (stomp.subscribe)
        config.enableSimpleBroker("/topic");

        // 2. Designate the prefix for messages that are bound for @MessageMapping-annotated methods in application code
        // /app/ → paths that client sends messages to (stomp.send)
        config.setApplicationDestinationPrefixes("/app");
    }
}
