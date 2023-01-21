package com.webapps.levelup.helper;

import com.webapps.levelup.configuration.AppProperties;
import com.webapps.levelup.exception.CustomException;
import com.webapps.levelup.model.user.request.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenHelper {

    @Value("${jwt.secret}")
    private String secret;
    private final AppProperties appProperties;

    public String getToken(UserEntity tokenData) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", tokenData.getId());
        claims.put("email", tokenData.getEmail());

        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS512)
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(
                        new Date(System.currentTimeMillis() + appProperties.getTokenExpiration() * 60 * 1000))
                .compact();
    }

    public String getRefreshToken(UserEntity tokenData) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", tokenData.getId());
        claims.put("email", tokenData.getEmail());

        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS512)
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(
                        new Date(System.currentTimeMillis() + appProperties.getRefreshTokenExpiration() * 60 * 1000))
                .compact();
    }

    public void refreshToken(String refreshToken, ExpiredJwtException ex) {
        try {
            if (refreshToken == null || !validateToken(refreshToken)) {
                log.error("Your session has expired. Refresh Token {}", refreshToken);
                throw new CustomException("Your session has expired");
            }

            final Claims claims = ex.getClaims();

            UserEntity tokenData =
                    new UserEntity() {
                        {
                            setId((Integer) claims.get("id"));
                            setUsername((String) claims.get("username"));
                            setEmail((String) claims.get("email"));
                        }
                    };
            getToken(tokenData);
            getRefreshToken(tokenData);
        } catch (ExpiredJwtException e) {
            log.error("Your session has expired. {}", e.getMessage(), e);
            throw new CustomException("Your session has expired");
        }
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public String getEmailFromToken(String token) {
        final Claims claims = getAllClaimsFromToken(token);
        return (String) claims.get("email");
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secret.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
