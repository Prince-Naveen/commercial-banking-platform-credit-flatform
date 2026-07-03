package com.naveen.bank.auth.producer;

import com.naveen.bank.auth.event.UserLockedEvent;
import com.naveen.bank.auth.event.UserRegisteredEvent;
import com.naveen.bank.auth.event.UserUnlockedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishUserRegistered(UserRegisteredEvent event) {

        kafkaTemplate.send("user-registered", event);

        log.info("UserRegisteredEvent Published");
    }

    public void publishUserLocked(UserLockedEvent event) {

        kafkaTemplate.send("user-locked", event);

        log.info("UserLockedEvent Published");
    }

    public void publishUserUnlocked(UserUnlockedEvent event) {

        kafkaTemplate.send("user-unlocked", event);

        log.info("UserUnlockedEvent Published");
    }

}