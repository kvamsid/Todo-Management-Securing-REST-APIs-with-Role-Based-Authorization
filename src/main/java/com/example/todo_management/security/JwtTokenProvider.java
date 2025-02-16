package com.example.todo_management.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private Long jwtExpirationDate;

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();

        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + jwtExpirationDate);

        String jwtToken = Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(expirationDate).signWith(getKey()).compact();
        return jwtToken;
    }

    private Key getKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    //Get username from token
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
        String username = claims.getSubject();
        return username;
    }

    //Validate Token
    public boolean validateToken(String token) {
        Jwts.parserBuilder().setSigningKey(getKey()).build().parse(token);
        return true;
    }
}
