package com.naveen.bank.auth.event;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLockedEvent {

    private UUID userId;

    private String username;

    private String reason;

    private LocalDateTime lockedAt;

}