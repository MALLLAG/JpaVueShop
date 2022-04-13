package com.example.JpaVueShop_backend.domain.coupon;

import com.example.JpaVueShop_backend.domain.user.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static com.example.JpaVueShop_backend.domain.coupon.QCoupon.coupon;

@Repository
public class AdminCouponRepoSup extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     *
     *
     */
    public AdminCouponRepoSup(JPAQueryFactory jpaQueryFactory) {
        super(Coupon.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    /**
     * 총 쿠폰 갯수 카운트
     * @return
     */
    public long couponCount() {
        return jpaQueryFactory
                .select(coupon)
                .from(coupon)
                .fetchCount();
    }

}
