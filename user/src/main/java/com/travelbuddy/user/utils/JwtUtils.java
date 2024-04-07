package com.travelbuddy.user.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Getter
public class JwtUtils {
     @Value("${security.secretToken}")
     private String SECRET_TOKEN;

     private String createJWTToken(Map<String, Object> claims, String userName) {
         return Jwts.builder()
                 .setClaims(claims).setSubject(userName).setIssuedAt(new Date(System.currentTimeMillis()))
                 .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60))
                 .signWith(getSignature(), SignatureAlgorithm.HS256).compact();
     }

     public String generateJWTToken(String userName) {
         return createJWTToken(new HashMap<String,Object>(), userName);
     }
     private Key getSignature() {
         byte[] keyBytes = Decoders.BASE64.decode(SECRET_TOKEN);
         return Keys.hmacShaKeyFor(keyBytes);
     }

     public Jws<Claims> validateJWTToken(String token) {
         return Jwts.parserBuilder().setSigningKey(getSignature()).build().parseClaimsJws(token);
     }
}
