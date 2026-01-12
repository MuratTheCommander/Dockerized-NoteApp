package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
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




}
