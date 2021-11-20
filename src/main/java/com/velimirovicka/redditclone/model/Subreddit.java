package com.velimirovicka.redditclone.model;

import static javax.persistence.GenerationType.*;

import java.time.Instant;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subreddit {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank(message = "Description is required")
    @Column(name = "message", nullable = false)
    private String description;

    @NotBlank(message = "Community name is required")
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "subreddit", orphanRemoval = true)
    private List<Post> posts;

    @Column(name = "createdDate", nullable = false)
    private Instant createdDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private User user;
}
