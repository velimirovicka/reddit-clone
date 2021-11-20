package com.velimirovicka.redditclone.repository;

import com.velimirovicka.redditclone.model.Post;
import com.velimirovicka.redditclone.model.User;
import com.velimirovicka.redditclone.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByIdDesc(Post post, User currentUser);
}
