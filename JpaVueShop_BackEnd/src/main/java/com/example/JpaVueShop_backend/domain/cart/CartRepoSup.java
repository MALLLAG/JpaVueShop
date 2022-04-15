package com.example.JpaVueShop_backend.domain.cart;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static com.example.JpaVueShop_backend.domain.cartItem.QCartItem.cartItem;

@Repository
public class CartRepoSup extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     *
     *
     */
    public CartRepoSup(JPAQueryFactory jpaQueryFactory) {
        super(Cart.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }


}
