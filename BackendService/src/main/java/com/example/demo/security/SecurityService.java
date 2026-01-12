package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
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

    public String[] splitJwt(String jwt){
        return jwt.split("\\.");
    }

    public String createSignature(String[] jwtParts){
        try{
            String encodedHeaderPayload = jwtParts[0] + "." + jwtParts[1];

            SecretKey secretKey = getSecretKey();

            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(secretKey);

            byte[] rawHmac = mac.doFinal(encodedHeaderPayload.getBytes(StandardCharsets.UTF_8));

            return Base64.getUrlEncoder()
                    .withoutPadding()
                    .encodeToString(rawHmac);

        } catch (Exception e) {
            throw new RuntimeException("Failed to create JWT signature",e);
        }


    }




}
