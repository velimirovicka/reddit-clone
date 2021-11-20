package com.velimirovicka.redditclone.controller;

import com.velimirovicka.redditclone.dto.PostRequest;
import com.velimirovicka.redditclone.dto.PostResponse;
import com.velimirovicka.redditclone.service.PostService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest) {
        postService.save(postRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPost(id));
    }

    @GetMapping("/by-subreddit/{id}")
    public ResponseEntity<List<PostResponse>> getPostBySubreddit(@PathVariable Long id) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getPostsBySubreddit(id));
    }

    @GetMapping("/by-user/{name}")
    public ResponseEntity<List<PostResponse>> getPostByUser(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostByUser(name));
    }
}
