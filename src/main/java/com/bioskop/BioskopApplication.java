package com.bioskop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class BioskopApplication {


    public static void main(String[] args) {
        SpringApplication.run(BioskopApplication.class, args);
    }

}