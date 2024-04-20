package com.geejaa.yo.domain;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record YoMessage(
        String content,
        String sender,
        MessageType messageType,
        LocalDateTime timestamp
) {
}
