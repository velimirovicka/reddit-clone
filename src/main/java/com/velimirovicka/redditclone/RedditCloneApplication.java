package com.velimirovicka.redditclone;

import com.velimirovicka.redditclone.config.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
public class RedditCloneApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedditCloneApplication.class, args);
    }
}
