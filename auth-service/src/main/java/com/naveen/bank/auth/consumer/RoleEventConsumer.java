package com.naveen.bank.auth.consumer;

import com.naveen.bank.auth.event.RoleAssignedEvent;
import com.naveen.bank.auth.event.RoleRevokedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RoleEventConsumer {

    @KafkaListener(
            topics = "role-assigned",
            groupId = "auth-group")
    public void consumeRoleAssigned(RoleAssignedEvent event) {

        log.info("Role Assigned Event Received : {}", event);
    }

    @KafkaListener(
            topics = "role-revoked",
            groupId = "auth-group")
    public void consumeRoleRevoked(RoleRevokedEvent event) {

        log.info("Role Revoked Event Received : {}", event);
    }

}