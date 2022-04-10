package com.example.JpaVueShop_backend.dto.api.user;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginReqDto {
    private String username;
    private String password;
}
