package com.velimirovicka.redditclone.controller;

import com.velimirovicka.redditclone.dto.CommentDto;
import com.velimirovicka.redditclone.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentDto commentDto){
        this.commentService.save(commentDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/by-post/{postId}")
    public ResponseEntity<List<CommentDto>> getAllCommentsForPost(@PathVariable Long postId){
        return ResponseEntity.status(HttpStatus.OK).body(this.commentService.getAllCommentsForPost(postId));
    }
    @GetMapping("/by-user/{userName}")
    public List<CommentDto> getAllCommentsForUser(@PathVariable String userName){
        return this.commentService.getAllCommentsForUser(userName);
    }
}
