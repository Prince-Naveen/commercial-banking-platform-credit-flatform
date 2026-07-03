package com.naveen.bank.auth.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginSuccessEvent {

    private UUID userId;

    private String username;

    private String ipAddress;

    private String device;

    private LocalDateTime loginTime;

}