package com.example.JpaVueShop_backend.dto.api.myPage;

import com.example.JpaVueShop_backend.domain.Role;
import com.example.JpaVueShop_backend.domain.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDataRespDto {
    private String username;
    private String password;
    private Role ROLE;

    public UserDataRespDto(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.ROLE = user.getROLE();
    }
}
