package com.velimirovicka.redditclone.mapper;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.velimirovicka.redditclone.dto.PostRequest;
import com.velimirovicka.redditclone.dto.PostResponse;
import com.velimirovicka.redditclone.model.Post;
import com.velimirovicka.redditclone.model.Subreddit;
import com.velimirovicka.redditclone.model.User;
import com.velimirovicka.redditclone.repository.CommentRepository;
import com.velimirovicka.redditclone.repository.VoteRepository;
import com.velimirovicka.redditclone.service.AuthService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private AuthService authService;

    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "voteCount", constant = "0")
    @Mapping(target = "id", ignore = true)
    public abstract Post map(PostRequest postRequest, Subreddit subreddit, User user);

    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.userName")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    @Mapping(target = "duration",expression = "java(getDuration(post))")
    public abstract PostResponse mapToDto(Post post);

    Integer commentCount(Post post){
        return this.commentRepository.findByPost(post).size();
    }

    String getDuration(Post post){
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }
}
