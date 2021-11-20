package com.velimirovicka.redditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class RegisterRequest {

    private String email;
    private String username;
    private String password;
}
