package com.study.springbootwebboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringbootWebBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootWebBoardApplication.class, args);
    }

}
