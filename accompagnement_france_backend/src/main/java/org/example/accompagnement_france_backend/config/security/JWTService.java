package org.example.accompagnement_france_backend.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.accompagnement_france_backend.auth.entity.RefreshToken;
import org.example.accompagnement_france_backend.auth.repository.RefreshTokenRepo;
import org.example.accompagnement_france_backend.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
@Service
public class JWTService {
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    @Autowired private RefreshTokenRepo refreshTokenRepo;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }


    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    private static long toDays(long milliseconds) {
        return TimeUnit.MILLISECONDS.toDays(milliseconds);
    }
    public RefreshToken generateJWTRefreshToken(User user){
        //will expire in one weak
        var refreshToken = generateRefreshToken(user);
        return RefreshToken.builder()
                .value(refreshToken)
                .timeToLiveDays(toDays(refreshExpiration))
                .expiresAt( Instant.now().plusMillis(refreshExpiration))
                .owner(user)
                .build();
    }
    public String generateTokenWithSpecificExpiration(
            UserDetails userDetails,
            Map<String,Object> tokenDetails
    ) {
        return buildToken(tokenDetails, userDetails, jwtExpiration);
    }

    public String generateRefreshToken(
            UserDetails userDetails
    ) {
        return buildToken(new HashMap<>(), userDetails, refreshExpiration);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        boolean isTokenValid = (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
        if(!isTokenValid) return false;
        //if the access token exist so it is in the black list
        RefreshToken revokedToken= refreshTokenRepo.findByValue(token);
        return  revokedToken==null ;

    }


    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public RefreshToken save(RefreshToken revokedToken) {
        return refreshTokenRepo.save(revokedToken);
    }

    public boolean isRefreshTokenValid(String refreshTokenValue){
        RefreshToken refreshToken = refreshTokenRepo.findByValue(refreshTokenValue);
        return refreshToken!=null&&!refreshToken.isRevoked();
    }
    public void setRefreshTokenExpiration(String refreshTokenValue){
        RefreshToken refreshToken = refreshTokenRepo.findByValue(refreshTokenValue);
        refreshToken.setExpiresAt(Instant.now());
        refreshTokenRepo.save(refreshToken);
    }
    public void revokeUserRefreshTokens(User user){
        refreshTokenRepo.revokeRefreshTokens(user);
    }
}
