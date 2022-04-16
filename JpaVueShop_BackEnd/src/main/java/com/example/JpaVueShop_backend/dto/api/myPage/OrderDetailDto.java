package com.example.JpaVueShop_backend.dto.api.myPage;

import com.example.JpaVueShop_backend.domain.OrderStatus;
import com.example.JpaVueShop_backend.domain.order.Order;
import com.example.JpaVueShop_backend.domain.user.User;
import com.example.JpaVueShop_backend.dto.api.order.OrderItemDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class OrderDetailDto {
    private String username;
    private String phone;
    private OrderStatus orderStatus;
    private List<OrderItemDto> orderItemDtoList = new ArrayList<>();
    private String orderTitle;
    private int originalPrice;
    private int paymentPrice;
    private int couponAmount;
    private int usedPoint;

    public OrderDetailDto(User user, Order order) {
        this.username = user.getUsername();
        this.orderStatus = order.getOrderStatus();
        this.orderTitle = order.getOrderTitle();
        this.originalPrice = order.getOriginalPrice();
        this.paymentPrice = order.getPaymentPrice();
        this.couponAmount = order.getCouponAmount();
        this.usedPoint = order.getUsedPoint();
    }

    public void addOrderItemDto(OrderItemDto orderItemDto) {
        this.orderItemDtoList.add(orderItemDto);
    }
}
