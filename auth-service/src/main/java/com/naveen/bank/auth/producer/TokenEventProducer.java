package com.naveen.bank.auth.producer;

import com.naveen.bank.auth.event.RefreshTokenCreatedEvent;
import com.naveen.bank.auth.event.RefreshTokenRevokedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishRefreshTokenCreated(
            RefreshTokenCreatedEvent event) {

        kafkaTemplate.send("refresh-token-created", event);

        log.info("RefreshTokenCreatedEvent Published");
    }

    public void publishRefreshTokenRevoked(
            RefreshTokenRevokedEvent event) {

        kafkaTemplate.send("refresh-token-revoked", event);

        log.info("RefreshTokenRevokedEvent Published");
    }

}