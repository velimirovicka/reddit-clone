package com.velimirovicka.redditclone.service;

import com.velimirovicka.redditclone.dto.PostRequest;
import com.velimirovicka.redditclone.dto.PostResponse;
import com.velimirovicka.redditclone.mapper.PostMapper;
import com.velimirovicka.redditclone.model.Post;
import com.velimirovicka.redditclone.model.Subreddit;
import com.velimirovicka.redditclone.model.User;
import com.velimirovicka.redditclone.repository.PostRepository;
import com.velimirovicka.redditclone.repository.SubredditRepository;
import com.velimirovicka.redditclone.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final SubredditRepository subredditRepository;
    private final AuthService authService;
    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostResponse save(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository
            .findByName(postRequest.getSubredditName())
            .orElseThrow(() -> new RuntimeException("Subreddit not found"));
        User currentUser = authService.getCurrentUser();
        Post post = postRepository.save(
            postMapper.map(postRequest, subreddit, currentUser)
        );
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("No post found"));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository
            .findAll()
            .stream()
            .map(postMapper::mapToDto)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubreddit(Long subredditId) {
        Subreddit subreddit = subredditRepository
            .findById(subredditId)
            .orElseThrow(() -> new RuntimeException("Subreddit not found"));
        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
        return posts.stream().map(postMapper::mapToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostByUser(String name) {
        User user = userRepository
            .findByUserName(name)
            .orElseThrow(() -> new RuntimeException("User not found"));
        List<Post> posts = postRepository.findAllByUser(user);
        return posts.stream().map(postMapper::mapToDto).collect(Collectors.toList());
    }
}
