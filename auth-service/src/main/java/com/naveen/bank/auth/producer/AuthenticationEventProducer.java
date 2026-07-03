package com.naveen.bank.auth.producer;

import com.naveen.bank.auth.event.FailedLoginEvent;
import com.naveen.bank.auth.event.LoginSuccessEvent;
import com.naveen.bank.auth.event.LogoutEvent;
import com.naveen.bank.auth.event.PasswordChangedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishLoginSuccess(LoginSuccessEvent event) {

        kafkaTemplate.send("login-success", event);

        log.info("LoginSuccessEvent Published");
    }

    public void publishFailedLogin(FailedLoginEvent event) {

        kafkaTemplate.send("failed-login", event);

        log.info("FailedLoginEvent Published");
    }

    public void publishLogout(LogoutEvent event) {

        kafkaTemplate.send("logout", event);

        log.info("LogoutEvent Published");
    }

    public void publishPasswordChanged(PasswordChangedEvent event) {

        kafkaTemplate.send("password-changed", event);

        log.info("PasswordChangedEvent Published");
    }

}