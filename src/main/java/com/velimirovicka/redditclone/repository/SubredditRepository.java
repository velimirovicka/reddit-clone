package com.velimirovicka.redditclone.repository;

import com.velimirovicka.redditclone.model.Subreddit;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubredditRepository extends JpaRepository<Subreddit, Long> {
    Optional<Subreddit> findByName(String name);
}
