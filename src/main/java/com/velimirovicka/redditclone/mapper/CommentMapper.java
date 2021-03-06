package com.velimirovicka.redditclone.mapper;

import com.velimirovicka.redditclone.dto.CommentDto;
import com.velimirovicka.redditclone.model.Comment;
import com.velimirovicka.redditclone.model.Post;
import com.velimirovicka.redditclone.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "commentDto.text")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    Comment map(CommentDto commentDto, Post post, User user);

    @Mapping(target = "postId", expression = "java(comment.getPost().getId())")
    @Mapping(target = "userName", source = "comment.user.userName")
    CommentDto mapToDto(Comment comment);
}
