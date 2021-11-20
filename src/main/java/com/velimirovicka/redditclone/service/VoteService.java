package com.velimirovicka.redditclone.service;

import com.velimirovicka.redditclone.dto.VoteDto;
import com.velimirovicka.redditclone.model.Post;
import com.velimirovicka.redditclone.model.Vote;
import com.velimirovicka.redditclone.model.VoteType;
import com.velimirovicka.redditclone.repository.PostRepository;
import com.velimirovicka.redditclone.repository.VoteRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@AllArgsConstructor
@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    @Transactional
    public void vote(VoteDto voteDto){

        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(()-> new RuntimeException("Post not found with ID - " + voteDto.getPostId()));
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByIdDesc(post, post.getUser());
        if (voteByPostAndUser.isPresent() &&
        voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())){
            throw new RuntimeException("You have alredy "+ voteDto.getVoteType()+ " 'd for this post");
        }
        if (VoteType.UPVOTE.equals(voteDto.getVoteType())){
            post.setVoteCount(post.getVoteCount()+1);
        }else {
            post.setVoteCount(post.getVoteCount());
        }
        this.voteRepository.save(mapToVote(voteDto, post));
        this.postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post){
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }

}
