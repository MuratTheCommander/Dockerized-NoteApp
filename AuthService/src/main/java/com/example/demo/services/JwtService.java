package com.example.demo.services;

import com.example.demo.dto.ResponseUserDto;
import com.example.demo.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {

    @Value("${accessToken.secretkey}")
    private String secretKey;

    private final long expirationMs = 5 * 60 * 1000; //5 minutes

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateJwt(ResponseUserDto user){
        Date expiryDate = new Date(System.currentTimeMillis() + expirationMs);
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("roles",user.getUserRoles())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(getSecretKey())
                .compact();
    }

    public Claims parseJwt(String jwtToken){
        try{
            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(jwtToken);
            return jws.getBody();
        }catch(Exception e){
            throw e;
        }
    }

    public String extractUsername(String jwtToken){
            return parseJwt(jwtToken).getSubject();
    }

    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String jwtToken){
        return parseJwt(jwtToken).get("roles",java.util.List.class);
    }

    public boolean isTokenExpired(String jwtToken){
        return parseJwt(jwtToken).getExpiration().before(new Date());
    }

    public boolean isTokenValid(String jwtToken){
        try{
            parseJwt(jwtToken);
            return true;
        }catch (JwtException | IllegalArgumentException e){
            return false;
        }
    }

    public boolean isTokenValidForUser(String jwtToken,User user){
        try{
            Claims claims = parseJwt(jwtToken);
            List<String> roles = parseJwt(jwtToken).get("roles", List.class);
            String userName = claims.getSubject();
            Date exp = claims.getExpiration();
            return userName.equals(user.getUsername()) && exp.after(new Date()) && roles.contains("ROLE_USER");
        }catch (JwtException | IllegalArgumentException e){
        return false;
        }
    }

    public boolean isTokenValidForUser(String jwtToken){
        try{
            Claims claims = parseJwt(jwtToken);
            List<String> roles = parseJwt(jwtToken).get("roles", List.class);
            String userName = claims.getSubject();
            Date exp = claims.getExpiration();
            return exp.after(new Date()) && roles.contains("ROLE_USER");
        }catch (JwtException | IllegalArgumentException e){
            return false;
        }
    }

}
