package com.example.JpaVueShop_backend.domain.order;

import com.example.JpaVueShop_backend.domain.OrderStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.JpaVueShop_backend.domain.order.QOrder.order;

@Repository
public class OrderRepoSup extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     *
     *
     */
    public OrderRepoSup(JPAQueryFactory jpaQueryFactory) {
        super(Order.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    /**
     * 주문내역 갯수 카운트
     * @param userId
     * @return
     */
    public long orderCount(Long userId) {
        return jpaQueryFactory
                .select(order)
                .from(order)
                .where(order.user.id.eq(userId))
                .fetchCount();
    }


}
