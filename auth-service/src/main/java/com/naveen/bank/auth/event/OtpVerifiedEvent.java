package com.naveen.bank.auth.event;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtpVerifiedEvent {

    private String username;

    private String email;

    private LocalDateTime verifiedAt;

}