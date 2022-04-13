package com.example.JpaVueShop_backend.domain.coupon;

import com.example.JpaVueShop_backend.dto.admin.coupon.PublishCouponDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Table(name = "coupon")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "couponId")
    private Long id;
    private String couponName;
    private String couponNumber;

    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date endDate;

    private int amount;
    private LocalDateTime regDate;

    @PrePersist
    public void regDate() {
        this.regDate = LocalDateTime.now();
    }

    /**
     * 쿠폰 생성
     * @param publishCouponDto
     * @return
     */
    public static Coupon createCoupon(PublishCouponDto publishCouponDto) {
        Coupon coupon = new Coupon();
        coupon.setCouponName(publishCouponDto.getCouponName());
        coupon.setCouponNumber(publishCouponDto.getCouponNumber());
        coupon.setAmount(publishCouponDto.getAmount());
        coupon.setStartDate(publishCouponDto.getStartDate());
        coupon.setEndDate(publishCouponDto.getEndDate());

        return coupon;
    }
}
