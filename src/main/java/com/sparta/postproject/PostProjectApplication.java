package com.sparta.postproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PostProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostProjectApplication.class, args);
    }

}
