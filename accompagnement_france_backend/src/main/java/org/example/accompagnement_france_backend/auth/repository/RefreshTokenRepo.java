package org.example.accompagnement_france_backend.auth.repository;

import org.example.accompagnement_france_backend.auth.entity.RefreshToken;
import org.example.accompagnement_france_backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Long> {
    RefreshToken findByValue(String value);


    @Modifying
    @Transactional
    @Query("DELETE FROM RefreshToken t WHERE t.expiresAt < CURRENT_TIMESTAMP")
    void deleteTokensBeforeCurrentTime();


    @Modifying
    @Transactional
    @Query("UPDATE RefreshToken t SET  t.revoked = true WHERE t.owner.email = :email")
    void revokeTokenByUserEmail(String email);


    @Modifying
    @Transactional
    @Query("UPDATE RefreshToken t SET t.revoked = true WHERE t.owner = :owner")
    void revokeRefreshTokens(User owner);


}
