package org.yasmani.io.apprestwithtoken.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.yasmani.io.apprestwithtoken.exception.JwtTokenException;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtToken {

    private final SecretKey SECRET_KEY;
    private static final int EXPIRATION_TIME_IN_MS = 8 * 60 * 1000; // 8 minutos
    private static final String AUTHORITIES = "authorities";

    public JwtToken() {
        this.SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String createToken(UserDetails userDetails) {
        try {
            return Jwts.builder()
                    .subject(userDetails.getUsername())
                    .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_IN_MS))
                    .claim(AUTHORITIES, userDetails.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toList()))
                    .signWith(SECRET_KEY)
                    .compact();
        } catch (Exception e) {
            throw new JwtTokenException("Error creating token");
        }
    }

    public Boolean hasTokenExpired(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !hasTokenExpired(token));
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public Collection getAuthorities(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get(AUTHORITIES, Collection.class);
    }

}
