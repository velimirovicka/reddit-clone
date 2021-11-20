package com.velimirovicka.redditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SubredditDto {

    private String name;
    private String description;
    private Integer numberOfPosts;
    private Long id;
}
