package org.example.accompagnement_france_backend.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class AuthResult {
    private String refreshToken;
    private String accessToken;
    private long refreshTokenDaysToLive;
    private boolean mustChangePassword;
}
