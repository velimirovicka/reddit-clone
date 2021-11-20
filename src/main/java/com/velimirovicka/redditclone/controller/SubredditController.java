package com.velimirovicka.redditclone.controller;

import com.velimirovicka.redditclone.dto.SubredditDto;
import com.velimirovicka.redditclone.service.SubredditService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
public class SubredditController {

    private final SubredditService subredditService;

    @PostMapping
    public ResponseEntity<SubredditDto> createSubreddit(
        @RequestBody SubredditDto subreditDto
    ) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(subredditService.save(subreditDto));
    }

    @GetMapping
    public ResponseEntity<List<SubredditDto>> getAllSubreddits() {
        return ResponseEntity.status(HttpStatus.OK).body(subredditService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubredditDto> getSubredditById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(subredditService.getById(id));
    }
}
