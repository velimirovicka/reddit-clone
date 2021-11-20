package com.velimirovicka.redditclone.service;

import com.velimirovicka.redditclone.dto.*;
import com.velimirovicka.redditclone.model.User;
import com.velimirovicka.redditclone.model.VerificationToken;
import com.velimirovicka.redditclone.repository.UserRepository;
import com.velimirovicka.redditclone.repository.VerificationTokenRepository;
import com.velimirovicka.redditclone.security.JwtProvider;

import java.time.Instant;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUserName(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(this.passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);

        String token = generateVerificationToken(user);
        NotificationEmail notificationEmail = new NotificationEmail();
        notificationEmail.setBody(
                "Thank you for signing up to Spring Reddit. " +
                        "Please click on the link below:" +
                        "http://localhost:8080/api/auth/accountVerification/" +
                        token
        );
        notificationEmail.setSubject("Please Activate your Account");
        notificationEmail.setRecipient(user.getEmail());
        mailService.sendMail(notificationEmail);
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(Instant.now().plusSeconds(3600));

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    @Transactional
    public void verifyAccount(String token) {
        var verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new RuntimeException("Invalid Token"));
        fetchUserAndEnable(verificationToken.get());
    }

    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUserName();
        User user = userRepository
                .findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found " + username));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );
        Authentication authenticate = authenticationManager.authenticate(
                authenticationToken
        );
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(loginRequest.getUsername());

        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .userName(loginRequest.getUsername())
                .build();
    }

    public User getCurrentUser() {
        var username = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        return userRepository.findByUserName(username).orElseThrow();
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateToken(refreshTokenRequest.getUsername());

        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .userName(refreshTokenRequest.getUsername())
                .build();
    }
}
