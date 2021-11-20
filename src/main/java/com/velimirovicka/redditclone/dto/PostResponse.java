package com.velimirovicka.redditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PostResponse {

    private Long id;
    private String postName;
    private String description;
    private String url;
    private String subredditName;
    private String userName;
    private Integer voteCount;
    private Integer commentCount;
    private String duration;

}
