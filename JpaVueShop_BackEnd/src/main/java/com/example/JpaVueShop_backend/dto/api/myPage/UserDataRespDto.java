package com.example.JpaVueShop_backend.dto.api.myPage;

import com.example.JpaVueShop_backend.domain.Role;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDataRespDto {
    private String username;
    private Role ROLE;
}
