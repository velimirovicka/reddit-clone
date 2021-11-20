package com.velimirovicka.redditclone.service;

import com.velimirovicka.redditclone.model.RefreshToken;
import com.velimirovicka.redditclone.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@Service
@Transactional
public class RefreshTokenService {

    public RefreshTokenRepository refreshTokenRepository;

    public RefreshToken generateRefreshToken(){
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());

        return refreshTokenRepository.save(refreshToken);
    }
    void validateRefreshToken(String token){
        refreshTokenRepository.findByToken(token)
                .orElseThrow(()-> new RuntimeException("Invalid refresh Token"));
    }

    public void deleteRefreshToken(String token){
        refreshTokenRepository.deleteByToken(token);
    }
}
