package org.example.ewastev0_1.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {
    private String jwtSecret;
    @Value("${jwt.secret}")
    public void setJwtSecret(String secret) {
        this.jwtSecret = secret;
    }
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    public String extracteLogin(String login){
        return extractClaim(login, Claims::getSubject);
    }

    public  <T> T extractClaim(String token, Function<Claims,T> clazz){
        final Claims claims=extractAllClaims(token);
        return clazz.apply(claims);
    }

    public  String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(),userDetails);
    }
    public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()

//                .setIssuer()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()  + 10 * 60 * 60 * 1000))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }




    public boolean validateToken(String token, UserDetails userDetails) {
        final String username=extracteLogin(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    private Key getSignInKey() {
        byte[] keyBytes = Base64.getDecoder().decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
