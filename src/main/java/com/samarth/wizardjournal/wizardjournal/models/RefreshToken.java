package com.samarth.wizardjournal.wizardjournal.models;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private boolean revoked;

    public RefreshToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.revoked = false;
    }

    public void revoke() {
        this.revoked = true;
    }
}