package org.example.accompagnement_france_backend.auth.controller;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.example.accompagnement_france_backend.auth.dto.*;
import org.example.accompagnement_france_backend.auth.entity.PasswordResetToken;
import org.example.accompagnement_france_backend.auth.service.AuthenticationService;
import org.example.accompagnement_france_backend.auth.service.PasswordResetTokenService;
import org.example.accompagnement_france_backend.config.security.JWTService;
import org.example.accompagnement_france_backend.config.security.RefreshCookieService;
import org.example.accompagnement_france_backend.demand.service.RateLimitService;
import org.example.accompagnement_france_backend.email.EmailNotificationService;
import org.example.accompagnement_france_backend.user.entity.User;
import org.example.accompagnement_france_backend.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@RestController()
@RequestMapping("/auth")
public class AuthenticationController {
@Autowired private AuthenticationService authenticationService;
@Autowired private RefreshCookieService refreshCookieService;
@Autowired private JWTService jwtService;

@PostMapping("/login")
public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request,
                                                    HttpServletResponse response) {
    AuthResult result = authenticationService.authenticate(request);

    ResponseCookie cookie = refreshCookieService.create(result.getRefreshToken());
    response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

    return ResponseEntity.ok(AuthenticationResponse.builder()
            .accessToken(result.getAccessToken())
            .mustChangePassword(result.isMustChangePassword())
            .build());
}
@PostMapping("/change-password")
public ResponseEntity<AuthenticationResponse> changePassword(@Valid @RequestBody ChangePasswordDto changePasswordDto,
                                                    HttpServletResponse response) throws AccessDeniedException {
    AuthResult result = authenticationService.changePassword(changePasswordDto);

    ResponseCookie cookie = refreshCookieService.create(result.getRefreshToken());
    response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

    return ResponseEntity.ok(AuthenticationResponse.builder()
            .accessToken(result.getAccessToken())
            .mustChangePassword(result.isMustChangePassword())
            .build());
}
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(
            @CookieValue(name = "refresh_token", required = false) String refreshToken
    ) {
    return authenticationService.refreshToken(refreshToken);
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgetPassword(@RequestBody ForgetPasswordRequest forgetPasswordRequest, HttpServletRequest servletRequest){
            return authenticationService.forgetPassword(forgetPasswordRequest,servletRequest);
        }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid ResetPasswordRequest resetPasswordRequest){
        authenticationService.resetPassword(resetPasswordRequest);
        return ResponseEntity.ok(
                Map.of("message", "Votre mot de passe a été modifié avec succès.")
        );
    }

}
