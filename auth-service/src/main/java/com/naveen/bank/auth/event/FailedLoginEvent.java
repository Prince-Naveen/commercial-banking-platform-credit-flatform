package com.naveen.bank.auth.event;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FailedLoginEvent {

    private String username;

    private String ipAddress;

    private String reason;

    private LocalDateTime failedAt;

}