package com.cgi.ipsen5.Service;

import com.cgi.ipsen5.Model.InvalidToken;
import com.cgi.ipsen5.Repository.InvalidTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {
    @Value("${jwt.secret-key}")
    private String secretKey;

    private final InvalidTokenRepository invalidTokenRepository;


    public String extractUserId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UUID userId) {
        return generateToken(Map.of(), userId);
    }

    public String generateToken(Map<String, Object> extraClaims, UUID userId) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userId.toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24 * 60))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UUID userID) {
        if (invalidTokenRepository.existsById(token)) {
            return false;
        }

        final String userId = extractUserId(token);
        return userId.equals(userID.toString()) && !isTokenExpired(token);
    }

    private Claims extractAllClaims(String token) {
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

    public void validateToken(String token) {
        Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token);
    }

    public void invalidateToken(String token) {
        InvalidToken invalidToken = new InvalidToken();
        invalidToken.setToken(token);
        invalidTokenRepository.save(invalidToken);
    }

}
