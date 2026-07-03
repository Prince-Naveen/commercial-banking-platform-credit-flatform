package com.naveen.bank.auth.event;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtpGeneratedEvent {

    private String username;

    private String email;

    private String mobile;

    private String otp;

    private LocalDateTime generatedAt;

}