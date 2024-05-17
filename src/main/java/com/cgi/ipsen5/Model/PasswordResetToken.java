package com.cgi.ipsen5.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@RequiredArgsConstructor
@Entity
@Table(name = "reset_token")
public class PasswordResetToken {

    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(nullable = false, unique = true)
    @JsonProperty
    private UUID id;

    @Column(name = "token")
    @JsonProperty
    private String token;

    @NonNull
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Column(name = "expiry_date")
    @JsonProperty
    private LocalDateTime expiryDate;

    public void setExpiryDate() {
        this.expiryDate = LocalDateTime.now().plusHours(24);
    }
}
