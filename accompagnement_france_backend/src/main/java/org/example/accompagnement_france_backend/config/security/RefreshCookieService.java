package org.example.accompagnement_france_backend.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RefreshCookieService {
    private final RefreshCookieProperties properties;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshTokenExpiration;
    public ResponseCookie create(String token) {
        return ResponseCookie.from("refresh_token", token)
                .httpOnly(true)
                .secure(properties.isSecure())
                .sameSite(properties.getSameSite())
                .path(properties.getPath())
                .maxAge(refreshTokenExpiration)
                .build();
    }

    public ResponseCookie clear() {
        return ResponseCookie.from("refresh_token", "")
                .httpOnly(true)
                .secure(properties.isSecure())
                .sameSite(properties.getSameSite())
                .path(properties.getPath())
                .maxAge(0)
                .build();
    }
}
