package com.naveen.bank.auth.consumer;

import com.naveen.bank.auth.event.RefreshTokenCreatedEvent;
import com.naveen.bank.auth.event.RefreshTokenRevokedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TokenEventConsumer {

    @KafkaListener(
            topics = "refresh-token-created",
            groupId = "auth-group")
    public void consumeRefreshTokenCreated(
            RefreshTokenCreatedEvent event) {

        log.info("Refresh Token Created Event Received : {}", event);
    }

    @KafkaListener(
            topics = "refresh-token-revoked",
            groupId = "auth-group")
    public void consumeRefreshTokenRevoked(
            RefreshTokenRevokedEvent event) {

        log.info("Refresh Token Revoked Event Received : {}", event);
    }

}