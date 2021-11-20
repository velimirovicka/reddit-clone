package com.velimirovicka.redditclone.mapper;

import com.velimirovicka.redditclone.dto.SubredditDto;
import com.velimirovicka.redditclone.model.Subreddit;
import com.velimirovicka.redditclone.model.User;
import com.velimirovicka.redditclone.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

@Mapper(componentModel = "spring")
public abstract class SubredditMapper {

    @Autowired
    private UserRepository userRepository;

    @Mapping(target = "numberOfPosts", expression = "java(subreddit.getPosts().size())")
    public abstract SubredditDto mapSubredditToDto(Subreddit subreddit);

    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "user", expression = "java(getUser())")
    public abstract Subreddit mapSubredditDtoToSubreddit(SubredditDto subredditDto);

    protected User getUser() {
        var username = (String) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();
        return userRepository.findByUserName(username).orElseThrow();
    }
}
