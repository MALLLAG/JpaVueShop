package com.example.JpaVueShop_backend.config.jwt;

import com.example.JpaVueShop_backend.handler.exeption.CustomApiException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service("jwtService")
public class JwtService {

    private static final String SECRET_KEY = "accessToken";

    public String createAccessToken(String subject, long time, Map<String, Object> userData) {
        if (time <= 0) {
            throw new RuntimeException("Expiry time must be greater than Zero : ["+time+"] ");
        }

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());
        JwtBuilder builder = Jwts.builder()
                .claim("userData", userData)
                .setSubject(subject)
                .signWith(signatureAlgorithm, signingKey);
        long nowTime = System.currentTimeMillis();
        builder.setExpiration(new Date(nowTime + time));
        return builder.compact();
    }

    public String createRefreshToken(long time) {
        long nowTime = System.currentTimeMillis();
        return Jwts.builder()
                .setExpiration(new Date(nowTime + time))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Long getUserId(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
            claims = null;
        }
        return Long.valueOf(claims.getSubject());
    }

    public Claims getUserData(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
            claims = null;
        }
        return claims;
    }

    public boolean isUsable(String jwt) {
        Claims claims;
        try{
            claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                    .parseClaimsJws(jwt)
                    .getBody();
            return true;
        }catch (Exception e) {
            throw new CustomApiException("회원만 이용가능한 서비스입니다.");
        }
    }

    public boolean isExpire(String token) {
        boolean isExpire = false;
        try {
            isExpire = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration()
                    .before(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isExpire;
    }



}
