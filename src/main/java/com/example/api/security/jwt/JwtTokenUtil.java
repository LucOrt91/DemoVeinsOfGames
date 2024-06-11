package com.example.api.security.jwt;

import com.example.api.security.model.JwtProperties;
import com.example.api.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Component
public class JwtTokenUtil implements Serializable {

    @Autowired
    JwtProperties jwtProperties;

    @Autowired
    UserService userService;

    public String generateTokenForUser(Long id, String username, String email, String roleId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtProperties.getExpiration() * 1000L);

        return Jwts.builder()
                .setSubject(username)
                .claim("userId", id)
                .claim("email", email)
                .claim("role", roleId)
                .claim("fullname", username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecret())
                .compact();
    }
}