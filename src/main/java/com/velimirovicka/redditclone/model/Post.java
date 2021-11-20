package com.velimirovicka.redditclone.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.time.Instant;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.Hibernate;

@ToString
@Entity
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank(message = "Post Name cannot be empty or Null")
    private String postName;

    private String url;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "voteCount", nullable = false)
    private Integer voteCount = 0;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    @ToString.Exclude
    private User user;

    @Column(name = "createdDate", nullable = false)
    private Instant createdDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subredditId", nullable = false)
    @ToString.Exclude
    private Subreddit subreddit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
