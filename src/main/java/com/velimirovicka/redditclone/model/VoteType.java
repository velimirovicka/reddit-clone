package com.velimirovicka.redditclone.model;

import lombok.Getter;

import java.util.Arrays;

public enum VoteType {
    UPVOTE(1),
    DOWNVOTE(-1);

    @Getter
    private int direction;

    VoteType(int direction) {
        this.direction = direction;
    }
    
    public static VoteType lookup(Integer direction){
        return Arrays.stream(VoteType.values())
                .filter(value-> direction.equals(value.direction))
                .findAny()
                .orElseThrow(()->new RuntimeException("Vote not found"));
    }
}
