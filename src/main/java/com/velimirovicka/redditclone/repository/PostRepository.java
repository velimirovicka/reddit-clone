package com.velimirovicka.redditclone.repository;

import com.velimirovicka.redditclone.model.Post;
import com.velimirovicka.redditclone.model.Subreddit;
import com.velimirovicka.redditclone.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findAllByUser(User user);
}
