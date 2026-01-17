package org.example.accompagnement_france_backend.auth.service;

import org.example.accompagnement_france_backend.auth.entity.PasswordResetToken;
import org.example.accompagnement_france_backend.auth.repository.PasswordResetTokenRepository;
import org.example.accompagnement_france_backend.exceptions.PasswordRefreshTokenNotValidException;
import org.example.accompagnement_france_backend.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;

import java.util.UUID;

@Service
public class PasswordResetTokenService {

    private final PasswordResetTokenRepository tokenRepository;

    public PasswordResetTokenService(PasswordResetTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }
    @Transactional
    public PasswordResetToken createToken(User user) {

        // Optional but recommended: one active token per user
        tokenRepository.deleteByUser(user);
        tokenRepository.flush();

        String token = UUID.randomUUID().toString();

        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(30); // 30 min

        PasswordResetToken resetToken =
                new PasswordResetToken(token, user, expiryDate);

        return tokenRepository.save(resetToken);
    }
    public PasswordResetToken getTokenIfValid(String token) {
        PasswordResetToken passwordResetToken =  tokenRepository.findByToken(token).orElseThrow(()->new PasswordRefreshTokenNotValidException("Le lien de réinitialisation du mot de passe est invalide ou a expiré."));
        if(passwordResetToken.getExpiresAt().isBefore(LocalDateTime.now())){
           throw new PasswordRefreshTokenNotValidException("Le lien de réinitialisation du mot de passe est invalide ou a expiré.");
        }
        return  passwordResetToken;
    }
}
