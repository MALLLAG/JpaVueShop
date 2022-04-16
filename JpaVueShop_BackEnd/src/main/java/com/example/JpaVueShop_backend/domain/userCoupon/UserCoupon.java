package com.example.JpaVueShop_backend.domain.userCoupon;

import com.example.JpaVueShop_backend.domain.coupon.Coupon;
import com.example.JpaVueShop_backend.domain.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Table(name = "usercoupon")
public class UserCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userCouponId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "couponId")
    private Coupon coupon;
    private String isUsed;

    /**
     * 유저쿠폰 생성
     * @param user
     * @param coupon
     * @return
     */
    public static UserCoupon createUserCoupon(User user, Coupon coupon) {
        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setUser(user);
        userCoupon.setCoupon(coupon);
        userCoupon.setIsUsed("N");
        return userCoupon;
    }

}
