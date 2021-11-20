package com.velimirovicka.redditclone.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.time.Instant;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Table(name = "token")
@Setter
@Getter
@AllArgsConstructor
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "token", nullable = false)
    private String token;

    @OneToOne(optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(name = "expiryDate", nullable = false)
    private Instant expiryDate;
}
