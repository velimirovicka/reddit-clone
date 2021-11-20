package com.velimirovicka.redditclone.model;

import static javax.persistence.GenerationType.*;

import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "Username is required")
    @Column(name = "user_name", nullable = false)
    private String userName;

    @NotBlank(message = "Password is required")
    @Column(name = "password", nullable = false)
    private String password;

    @Email
    @NotEmpty(message = "Email is required")
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "created", nullable = false)
    private Instant created;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = false;
}
