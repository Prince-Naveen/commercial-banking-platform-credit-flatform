package com.naveen.bank.auth.producer;

import com.naveen.bank.auth.event.RoleAssignedEvent;
import com.naveen.bank.auth.event.RoleRevokedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishRoleAssigned(RoleAssignedEvent event) {

        kafkaTemplate.send("role-assigned", event);

        log.info("RoleAssignedEvent Published");
    }

    public void publishRoleRevoked(RoleRevokedEvent event) {

        kafkaTemplate.send("role-revoked", event);

        log.info("RoleRevokedEvent Published");
    }

}