package com.example.JpaVueShop_backend.domain.orderItem;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static com.example.JpaVueShop_backend.domain.orderItem.QOrderItem.orderItem;

@Repository
public class OrderItemRepoSup extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     *
     *
     */
    public OrderItemRepoSup(JPAQueryFactory jpaQueryFactory) {
        super(OrderItem.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }


}
