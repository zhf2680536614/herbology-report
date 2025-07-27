package com.herbology;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.herbology.mapper")
public class HerbologyApplication {
    public static void main(String[] args) {
        SpringApplication.run(HerbologyApplication.class, args);
    }
}
