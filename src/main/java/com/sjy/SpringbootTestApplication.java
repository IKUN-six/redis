package com.sjy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootTestApplication {

    public static void main(String[] args) {
        System.out.println("你好");
        System.out.println("不好");
        SpringApplication.run(SpringbootTestApplication.class, args);
    }

}
