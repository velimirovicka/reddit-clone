package com.velimirovicka.redditclone.repository;

import com.velimirovicka.redditclone.model.VerificationToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository
    extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);
}
