package com.example.JpaVueShop_backend.dto.api;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginReqDto {
    private String username;
    private String password;
}
