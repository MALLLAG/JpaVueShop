package com.example.JpaVueShop_backend.dto.admin.user;

import com.example.JpaVueShop_backend.domain.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserRespDto {
    private Long userId;
    private String username;
    private String password;

    public UserRespDto(User user) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
    }
}
