package com.naveen.bank.auth.consumer;

import com.naveen.bank.auth.event.UserLockedEvent;
import com.naveen.bank.auth.event.UserRegisteredEvent;
import com.naveen.bank.auth.event.UserUnlockedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserEventConsumer {

    @KafkaListener(
            topics = "user-registered",
            groupId = "auth-group")
    public void consumeUserRegistered(UserRegisteredEvent event) {

        log.info("User Registered Event Received : {}", event);
    }

    @KafkaListener(
            topics = "user-locked",
            groupId = "auth-group")
    public void consumeUserLocked(UserLockedEvent event) {

        log.info("User Locked Event Received : {}", event);
    }

    @KafkaListener(
            topics = "user-unlocked",
            groupId = "auth-group")
    public void consumeUserUnlocked(UserUnlockedEvent event) {

        log.info("User Unlocked Event Received : {}", event);
    }

}