package com.example.JpaVueShop_backend.dto.api.order;

import com.example.JpaVueShop_backend.domain.orderItem.OrderItem;
import com.example.JpaVueShop_backend.domain.review.Review;
import com.example.JpaVueShop_backend.dto.api.myPage.ReviewInOrderDto;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderItemDto {
    private Long itemId;
    private int price;
    private int orderPrice;
    private ReviewInOrderDto reviewInOrderDto;
    private boolean reviewEmpty;
    private String sampleFile;
    private String thumbnail;
    private String title;
    private String author;
    private String mainCategory;

    public OrderItemDto(OrderItem orderItem) {
        this.itemId = orderItem.getItem().getId();
        this.price = orderItem.getItem().getPrice();
        this.orderPrice = orderItem.getOrderPrice();
    }

    public void addReviewInOrderDto(Review review) {
        if (review != null) {
            ReviewInOrderDto reviewInOrderDto = new ReviewInOrderDto(review);
            this.reviewInOrderDto = reviewInOrderDto;
        } else {
            ReviewInOrderDto reviewInOrderDto = new ReviewInOrderDto();
            this.reviewInOrderDto = reviewInOrderDto;
        }
    }
}
