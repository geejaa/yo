package com.geejaa.yo.controller;

import com.geejaa.yo.domain.YoMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.Objects;

@Controller
public class YoController {

    @MessageMapping("/yo.send")
    @SendTo("/topic/public")
    public YoMessage sendPublicMessage(@Payload YoMessage message) {
        return message;
    }

    @MessageMapping("/yo.join")
    @SendTo("/topic/public")
    public YoMessage addUser(@Payload YoMessage message, SimpMessageHeaderAccessor headerAccessor) {
        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("username", message.sender());
        return message;
    }
}
