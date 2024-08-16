package com.psc.gx2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class Gx2Application {

    public static void main(String[] args) {
        SpringApplication.run(Gx2Application.class, args);
    }

}
