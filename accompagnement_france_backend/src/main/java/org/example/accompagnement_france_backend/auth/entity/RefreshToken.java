package org.example.accompagnement_france_backend.auth.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.accompagnement_france_backend.user.entity.User;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;


    @Column(length = 10000)
    private String value;

    private boolean revoked;
    private long timeToLiveDays;

    @ManyToOne
    private User owner;

    private Instant expiresAt;
}
