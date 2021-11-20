package com.velimirovicka.redditclone.model;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotEmpty
    private String text;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "postId")
    private Post post;

    private Instant createdDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId")
    private User user;
}
