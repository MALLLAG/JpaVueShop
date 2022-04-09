package com.example.JpaVueShop_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class JpaVueShopBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaVueShopBackEndApplication.class, args);
    }

}
