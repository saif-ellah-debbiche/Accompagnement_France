package org.example.accompagnement_france_backend.config.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.example.accompagnement_france_backend.auth.repository.RefreshTokenRepo;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;

@Component
@AllArgsConstructor
public class SpecialLogoutHandler implements LogoutHandler {
    private final RefreshTokenRepo tokenRepository;
    private final JWTService jwtService;
    private final RefreshCookieService refreshCookieService;
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String refreshToken = extractRefreshToken(request);
        if (refreshToken != null) {
            String userEmail = jwtService.extractUsername(refreshToken);
            tokenRepository.revokeTokenByUserEmail(userEmail);
        }

        response.addHeader(
                HttpHeaders.SET_COOKIE,
                refreshCookieService.clear().toString()
        );
    }

    private String extractRefreshToken(HttpServletRequest request) {
        if (request.getCookies() == null) return null;

        for (Cookie cookie : request.getCookies()) {
            if ("refresh_token".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

}

