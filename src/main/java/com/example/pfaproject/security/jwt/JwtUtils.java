package com.example.pfaproject.security.jwt;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${app.jwtCookiesName")
    private String jwtCookies;


    private ResponseCookie getCleanJwtCookies(){
        return ResponseCookie.from(jwtCookies,   null).path("/api").build();
    }

    public boolean validateJwtToken(String authToken){
        if(authToken == null || authToken.isEmpty()){
            return false;
        }
        try {
            SecretKey secret = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
            Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(authToken);
            return true;

        } catch (Exception Ignored) {}

        return false;
    }

    public String getUserNameFromJwtToken(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        Claims claims= Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        System.out.println("Token Claims: "+claims);
        return claims.getSubject();
    }


    public String generateJwtToken(UserDetails userPrincipal) {
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() +jwtExpirationMs))
                .claim("roles", userPrincipal.getAuthorities())
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .compact();
    }

    public ResponseCookie getCleanJwtCookie(){
        return ResponseCookie.from(jwtCookies,   null).path("/api").build();
    }

}
