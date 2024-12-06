package com.example.spring.boot.project.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "1b0a024a24194de896e2234c1e790cb92d1406686d00ec257258a4515530aee974b501c8bfa66caaaa5a25034eaf6fd577484ba1cc6d9ff10dcc8eb5215235c1545ccf72db5ca3493213785899c59e5ad35b1eef74b84f91649951102f7073342389ff14156b40ec0bbd59e378692c48367f96aa0381a03f686fbe6a3ba21f25ce3fa9d12153a394e4c3e112f87b4ed75d60ffafece67b97e3f451db38c1a193f57f4b285abe1c68494074334e4a038dbb5e70535497034d3f50a882a05040090e22961af85e869a946942c008c1cd46d8f46b2adcf767ba176d8fd4d409766e4b82b713ff6b2c19dcd56f2832e155a6d25bd09be4ea3deeb03f6ae9138e98c0";

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }

}
