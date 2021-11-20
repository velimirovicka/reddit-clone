package com.velimirovicka.redditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentDto {
    private Long id;
    private Long postId;
    private Instant createdDate;
    private String text;
    private String userName;
}
