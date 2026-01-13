package com.example.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;


@Service
public class SecurityService {

    @Value("${accessToken.secretkey}")
    private String secretKey;

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
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

    public List<String> extractRoles(String jwtToken){
        return parseJwt(jwtToken).get("roles",java.util.List.class);
    }

    public boolean validateJwt(String jwt){
        if(jwt == null || jwt.isBlank()){
            return false;
        }
        try{
          Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(jwt.trim())
                  .getBody();



            Date now = new Date();
            Date exp = claims.getExpiration();

            System.out.println("SERVER NOW = " + now);
            System.out.println("TOKEN EXP  = " + exp);
            System.out.println("MILLIS LEFT = " + (exp.getTime() - now.getTime()));

            return true;
        } catch (JwtException e){
            return false;
        }
    }



}
