package com.travelbuddy.user.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Getter
public class JwtUtils {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final RSAPublicKey publicKey;
    private final RSAPrivateKey privateKey;

    // Constructor Injection to avoid circular dependencies
    public JwtUtils(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder, RSAPublicKey publicKey, RSAPrivateKey privateKey) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public String createJWTToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    public String generateJWTToken(String userName) {
        return createJWTToken(new HashMap<>(), userName);
    }

    public Jws<Claims> validateJWTToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token);
    }
}
