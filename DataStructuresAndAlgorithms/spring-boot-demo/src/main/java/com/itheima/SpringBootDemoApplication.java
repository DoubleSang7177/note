package com.itheima;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class SpringBootDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoApplication.class, args);
//        SpringApplication app = new SpringApplication(SpringBootDemoApplication.class);
//        app.setDefaultProperties(Collections.singletonMap("server.port", "8083"));
//        app.run(args);
    }

}
