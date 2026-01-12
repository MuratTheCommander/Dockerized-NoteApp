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
import java.security.MessageDigest;
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

    public boolean validateJwt(String clientJwt){

        if (clientJwt == null || clientJwt.isBlank()) return false;

        String[] jwtParts = clientJwt.split("\\.");

        if (jwtParts.length != 3) return false;

        String secureSignature;

        try{
            String encodedHeaderPayload = jwtParts[0] + "." + jwtParts[1];

            SecretKey secretKey = getSecretKey();

            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(secretKey);

            byte[] rawHmac = mac.doFinal(encodedHeaderPayload.getBytes(StandardCharsets.UTF_8));

            secureSignature = Base64.getUrlEncoder()
                    .withoutPadding()
                    .encodeToString(rawHmac);

            return MessageDigest.isEqual(
                    jwtParts[2].getBytes(StandardCharsets.UTF_8),
                    secureSignature.getBytes(StandardCharsets.UTF_8)
            );

        } catch (Exception e) {
            return false;
        }
    }

}
