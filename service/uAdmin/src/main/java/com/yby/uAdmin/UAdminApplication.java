package com.yby.uAdmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan(basePackages = {"com.yby"})
@EnableSwagger2
public class UAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(UAdminApplication.class, args);
    }
}
