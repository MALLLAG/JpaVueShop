package com.example.JpaVueShop_backend.dto.admin.order;

import com.example.JpaVueShop_backend.domain.order.Order;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class OrderRespDto {
    // 유저 데이터
    private String username;

    // 주문 데이터
    private Long orderId;
    private String orderTitle;
    private LocalDateTime regDate;

    public OrderRespDto(Order order) {
        this.username = order.getUser().getUsername();

        this.orderId = order.getId();
        this.orderTitle = order.getOrderTitle();
        this.regDate = order.getRegDate();
    }
}
