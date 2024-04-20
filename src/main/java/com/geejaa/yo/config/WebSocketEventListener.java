package com.geejaa.yo.config;

import com.geejaa.yo.domain.MessageType;
import com.geejaa.yo.domain.YoMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messageSendingOperations;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = String.valueOf(Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("username"));

        if (username != null) {
            log.info("User {} disconnected from WebSocket", username);
            var message = YoMessage.builder()
                    .messageType(MessageType.LEAVE)
                    .sender(username)
                    .timestamp(LocalDateTime.now())
                    .build();

            messageSendingOperations.convertAndSend("/topic/public", message);
        }

    }
}
