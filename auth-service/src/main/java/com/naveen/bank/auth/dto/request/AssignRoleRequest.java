package com.naveen.bank.auth.dto.request;

import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignRoleRequest {

    private UUID userId;

    private Set<String> roles;
}