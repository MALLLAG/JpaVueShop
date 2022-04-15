package com.example.JpaVueShop_backend.dto.api.cart;

import com.example.JpaVueShop_backend.domain.user.User;
import com.example.JpaVueShop_backend.dto.api.myPage.UserCouponDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class OrderUserDataDto {
    private String username;
    private String name;
    private String email;
    private String phone;
    private int point;
    private List<UserCouponDto> userCouponDtoList = new ArrayList<>();

    public OrderUserDataDto(User user) {
        this.username = user.getUsername();
        this.point = user.getPoint();
    }

    public void addUserCouponDtoList(UserCouponDto userCouponDto) {
        this.userCouponDtoList.add(userCouponDto);
    }
}
