package com.example.demo.security;

import com.example.demo.entities.User;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    public static final Logger LOG = LoggerFactory.getLogger(JwtTokenProvider.class);

    public String generateToken(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());
        Date expirationTime = new Date(now.getTime() + SecurityConstants.EXPIRATION_TIME);

        String userId = Long.toString(user.getId());

        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("username", user.getUsername());
        claimsMap.put("role", user.getRole());

        return Jwts.builder()
                .setSubject(userId)
                .addClaims(claimsMap)
                .setIssuedAt(now)
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
                .compact();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser()
                    .setSigningKey(SecurityConstants.SECRET)
                    .parseClaimsJws(token);
            return true;
        }catch (SignatureException |
                MalformedJwtException |
                ExpiredJwtException |
                UnsupportedJwtException |
                IllegalArgumentException exception){
            LOG.error(exception.getMessage());
            return false;
        }
    }


    public long getUserIdFromToken(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(SecurityConstants.SECRET)
                .parseClaimsJws(token)
                .getBody();
        String id = (String) claims.get("id");
        return Long.parseLong(id);
    }

}