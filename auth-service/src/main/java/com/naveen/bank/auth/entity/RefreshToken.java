package com.naveen.bank.auth.entity;

import com.naveen.bank.auth.enums.TokenType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "refresh_tokens",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "token")
        }
)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, exclude = "user")
public class RefreshToken extends BaseEntity {

    @Column(name = "token", nullable = false, unique = true, length = 500)
    private String token;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "token_type", nullable = false, length = 20)
//    @Builder.Default
//    private TokenType tokenType = TokenType.BEARER;

    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;

    @Column(name = "revoked", nullable = false)
    @Builder.Default
    private Boolean revoked = false;

    @Column(name = "expired", nullable = false)
    @Builder.Default
    private Boolean expired = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_refresh_token_user")
    )
    private User user;
}