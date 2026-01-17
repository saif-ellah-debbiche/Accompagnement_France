package org.example.accompagnement_france_backend.auth.service;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.accompagnement_france_backend.auth.dto.*;
import org.example.accompagnement_france_backend.auth.entity.PasswordResetToken;
import org.example.accompagnement_france_backend.auth.entity.RefreshToken;
import org.example.accompagnement_france_backend.config.security.JWTService;
import org.example.accompagnement_france_backend.demand.service.RateLimitService;
import org.example.accompagnement_france_backend.email.EmailNotificationService;
import org.example.accompagnement_france_backend.user.entity.User;
import org.example.accompagnement_france_backend.user.repository.UserRepository;
import org.example.accompagnement_france_backend.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired private PasswordResetTokenService passwordResetTokenService;
    @Autowired private EmailNotificationService emailNotificationService;
    @Autowired private RateLimitService rateLimitService;
    @Autowired private UserService userService;


    public AuthResult authenticate(AuthenticationRequest authenticateRequest){
        User user = userRepository.findByEmail(authenticateRequest.getEmail()).orElse(null);
        if(user==null||!passwordEncoder.matches(authenticateRequest.getPassword(),user.getPassword())){
            throw new BadCredentialsException("email or password is not correct");
        }
        user.setLastLogin(new Date());
        userRepository.save(user);
       return createAuthenticationResponse(user);

    }


    public AuthResult changePassword(ChangePasswordDto changePasswordDto) throws AccessDeniedException {
       User connectedUser = getConnectedUser();
        assert connectedUser != null;
        if(!passwordEncoder.matches(changePasswordDto.getOldPassword(),connectedUser.getPassword())){
            throw new BadCredentialsException("password is not correct");
        }
        if(!changePasswordDto.getNewPassword().equals(changePasswordDto.getNewPasswordConfirm())){
            throw new BadCredentialsException("the new password and confirm password does not match ");
        }
        changeUserPassword(connectedUser,changePasswordDto.getNewPassword());
        return createAuthenticationResponse(connectedUser);
    }






    @Transactional
    public ResponseEntity<?> forgetPassword(@RequestBody ForgetPasswordRequest forgetPasswordRequest, HttpServletRequest servletRequest){
        String ip = servletRequest.getRemoteAddr();
        if (rateLimitService.isRateLimited(ip)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body((Map.of("message", "Limite de requêtes dépassée. Veuillez réessayer ultérieurement.")));
        }
        if(userService.existUserWithEmail(forgetPasswordRequest.getEmail())){
            User user =userService.getUserByEmail(forgetPasswordRequest.getEmail());
            PasswordResetToken token = passwordResetTokenService.createToken(user);
            String tokenValue = token.getToken();
            emailNotificationService.sendResetPasswordEmail(user.getEmail(),tokenValue);
            return ResponseEntity.ok(
                    Map.of("message", "Un email de réinitialisation du mot de passe a été envoyé avec succès.")
            );

        }
        return ResponseEntity.ok(
                Map.of("message", "Si l’adresse email est valide, un lien de réinitialisation vous a été envoyé.")
        );
    }


public void resetPassword(ResetPasswordRequest resetPasswordRequest){
    if(!resetPasswordRequest.getNewPassword().equals(resetPasswordRequest.getConfirmNewPassword())) {
        throw new IllegalArgumentException("Les mots de passe ne correspondent pas.");
    }
    PasswordResetToken passwordResetToken = passwordResetTokenService.getTokenIfValid(resetPasswordRequest.getToken());
    User user = passwordResetToken.getUser();
    jwtService.revokeUserRefreshTokens(user);

    changeUserPassword(user,resetPasswordRequest.getNewPassword());
}



    private User getConnectedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null; // no authenticated user
        }
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            // Your custom User object might be inside UserDetails
            return userRepository.findByEmail(userDetails.getUsername()).orElse(null);
        }
        return null;
    }
    protected void changeUserPassword(User user, String newPassword){
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setMustChangePassword(false);
        userRepository.save(user);
    }

    public AuthResult createAuthenticationResponse(User user){
        Map<String, Object> tokenDetails=new HashMap<>();
        tokenDetails.put("role",user.getRole());
        String accessToken = jwtService.generateTokenWithSpecificExpiration(user,tokenDetails);
        RefreshToken refreshToken = jwtService.generateJWTRefreshToken(user);
        jwtService.revokeUserRefreshTokens(user);
        jwtService.save(refreshToken);
        return AuthResult.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getValue())
                .refreshTokenDaysToLive(refreshToken.getTimeToLiveDays())
                .mustChangePassword(user.isMustChangePassword())
                .build();
    }


    public ResponseEntity<?> refreshToken(String refreshToken) {
        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("{\"message\": \"Refresh token is missing\"}");
        }

        try {
            String userEmail = jwtService.extractUsername(refreshToken);

            User user = userService.getUserByEmail(userEmail);

            // 4. Validate the token signature and user details
            if (user==null||!jwtService.isRefreshTokenValid(refreshToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("{\"message\": \"Refresh token is invalid\"}");
            }
            Map<String, Object> tokenDetails=new HashMap<>();
            tokenDetails.put("role",user.getRole());
            String accessToken = jwtService.generateTokenWithSpecificExpiration(user,tokenDetails);
            return ResponseEntity.ok(AuthenticationResponse.builder()
                    .accessToken(accessToken)
                    .mustChangePassword(user.isMustChangePassword())
                    .build());

        } catch (ExpiredJwtException e) {
            jwtService.setRefreshTokenExpiration(refreshToken);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("{\"message\": \"Refresh token has expired. Please login again.\"}");

        } catch (JwtException | IllegalArgumentException e) {
            // CATCH: Token is tampered or empty
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("{\"message\": \"Invalid refresh token.\"}");
        }
    }
}
