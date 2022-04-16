package com.example.JpaVueShop_backend.dto.api.myPage;

import com.example.JpaVueShop_backend.domain.OrderStatus;
import com.example.JpaVueShop_backend.domain.order.Order;
import com.example.JpaVueShop_backend.dto.api.order.OrderItemDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class OrderHistRespDto {
    private Long orderId;
    private String orderTitle;
    private OrderStatus orderStatus;
    private int paymentPrice;
    private LocalDateTime regDate;
    private List<OrderItemDto> orderItemDtoList = new ArrayList<>();

    public OrderHistRespDto(Order order) {
        this.orderId = order.getId();
        this.orderTitle = order.getOrderTitle();
        this.orderStatus = order.getOrderStatus();
        this.paymentPrice = order.getPaymentPrice();
        this.regDate = order.getRegDate();
    }

    public void addOrderItemDto(OrderItemDto orderItemDto) {
        this.orderItemDtoList.add(orderItemDto);
    }
}
