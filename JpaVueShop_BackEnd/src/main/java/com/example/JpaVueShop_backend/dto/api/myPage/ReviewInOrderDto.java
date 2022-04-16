package com.example.JpaVueShop_backend.dto.api.myPage;

import com.example.JpaVueShop_backend.domain.review.Review;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ReviewInOrderDto {
    private Long reviewId;
    private int reviewStar1;
    private LocalDateTime regDate;

    public ReviewInOrderDto(Review review) {
        this.reviewId = review.getId();
        this.reviewStar1 = review.getReviewStar1();
        this.regDate = review.getRegDate();
    }

    public ReviewInOrderDto() {}
}
