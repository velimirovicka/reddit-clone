package com.velimirovicka.redditclone.controller;

import com.velimirovicka.redditclone.dto.VoteDto;
import com.velimirovicka.redditclone.service.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/votes")
public class VoteController {

    private final VoteService voteService;
    @PostMapping
    public ResponseEntity<Void> vote(@RequestBody VoteDto voteDto){
        this.voteService.vote(voteDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
