package com.velimirovicka.redditclone.security;

import static io.jsonwebtoken.Jwts.parser;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.KeyStore;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;

@Service
public class JwtProvider {

    private KeyStore keyStore;
    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMillis;

    private final JwtParser parser = Jwts
        .parserBuilder()
        .setSigningKey(
            Base64.getEncoder().encode("secretsecretsecretsecretsecretsecret".getBytes())
        )
        .build();

    public String generateToken(String userName) {
        return Jwts
            .builder()
            .setSubject(userName)
            .signWith(
                SignatureAlgorithm.HS256,
                Base64
                    .getEncoder()
                    .encode("secretsecretsecretsecretsecretsecret".getBytes())
            )
                .setExpiration(Date.from(Instant.now().plusMillis(this.jwtExpirationInMillis)))
            .compact();
    }

    public boolean validateToken(String jwt) {
        parser.parse(jwt);
        return true;
    }

    public String getUsernameFromJwt(String token) {
        return parser.parseClaimsJws(token).getBody().getSubject();
    }

    public Long getJwtExpirationInMillis(){
        return jwtExpirationInMillis;
    }
}
