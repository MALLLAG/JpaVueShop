package com.example.JpaVueShop_backend.domain.userCoupon;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.JpaVueShop_backend.domain.userCoupon.QUserCoupon.userCoupon;

@Repository
public class UserCouponRepoSup extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     *
     *
     */
    public UserCouponRepoSup(JPAQueryFactory jpaQueryFactory) {
        super(UserCoupon.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    /**
     * 유저의 쿠폰 리스트 가져오기
     * @param userId
     * @return
     */
    public List<UserCoupon> getUserCouponList(Long userId) {
        return jpaQueryFactory
                .select(userCoupon)
                .from(userCoupon)
                .where(userCoupon.user.id.eq(userId)
                        .and(userCoupon.isUsed.eq("N")))
                .fetch();
    }

    /**
     * 사용한 쿠폰 isUsed = Y 로 변경
     * @param userCouponId
     */
    public void updateUserCoupon(Long userCouponId) {
        jpaQueryFactory
                .update(userCoupon)
                .set(userCoupon.isUsed, "Y")
                .where(userCoupon.id.eq(userCouponId))
                .execute();
    }


}
