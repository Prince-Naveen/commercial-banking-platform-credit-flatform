package com.naveen.bank.auth.consumer;

import com.naveen.bank.auth.event.OtpGeneratedEvent;
import com.naveen.bank.auth.event.OtpVerifiedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OtpEventConsumer {

    @KafkaListener(
            topics = "otp-generated",
            groupId = "auth-group")
    public void consumeOtpGenerated(OtpGeneratedEvent event) {

        log.info("OTP Generated Event Received : {}", event);
    }

    @KafkaListener(
            topics = "otp-verified",
            groupId = "auth-group")
    public void consumeOtpVerified(OtpVerifiedEvent event) {

        log.info("OTP Verified Event Received : {}", event);
    }

}