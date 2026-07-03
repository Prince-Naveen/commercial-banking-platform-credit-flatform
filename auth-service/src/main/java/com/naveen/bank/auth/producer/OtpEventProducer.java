package com.naveen.bank.auth.producer;

import com.naveen.bank.auth.event.OtpGeneratedEvent;
import com.naveen.bank.auth.event.OtpVerifiedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OtpEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishOtpGenerated(OtpGeneratedEvent event) {

        kafkaTemplate.send("otp-generated", event);

        log.info("OtpGeneratedEvent Published");
    }

    public void publishOtpVerified(OtpVerifiedEvent event) {

        kafkaTemplate.send("otp-verified", event);

        log.info("OtpVerifiedEvent Published");
    }

}