package com.naveen.bank.auth.consumer;

import com.naveen.bank.auth.event.FailedLoginEvent;
import com.naveen.bank.auth.event.LoginSuccessEvent;
import com.naveen.bank.auth.event.LogoutEvent;
import com.naveen.bank.auth.event.PasswordChangedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthenticationEventConsumer {

    @KafkaListener(
            topics = "login-success",
            groupId = "auth-group")
    public void consumeLoginSuccess(LoginSuccessEvent event) {

        log.info("Login Success Event Received : {}", event);
    }

    @KafkaListener(
            topics = "failed-login",
            groupId = "auth-group")
    public void consumeFailedLogin(FailedLoginEvent event) {

        log.info("Failed Login Event Received : {}", event);
    }

    @KafkaListener(
            topics = "logout",
            groupId = "auth-group")
    public void consumeLogout(LogoutEvent event) {

        log.info("Logout Event Received : {}", event);
    }

    @KafkaListener(
            topics = "password-changed",
            groupId = "auth-group")
    public void consumePasswordChanged(PasswordChangedEvent event) {

        log.info("Password Changed Event Received : {}", event);
    }

}