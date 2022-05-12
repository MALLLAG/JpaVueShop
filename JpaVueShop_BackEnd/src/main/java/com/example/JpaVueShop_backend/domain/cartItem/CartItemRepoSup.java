package com.example.JpaVueShop_backend.domain.cartItem;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.JpaVueShop_backend.domain.cartItem.QCartItem.cartItem;
import static com.example.JpaVueShop_backend.domain.cart.QCart.cart;

@Repository
public class CartItemRepoSup extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     *
     *
     */
    public CartItemRepoSup(JPAQueryFactory jpaQueryFactory) {
        super(CartItem.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    /**
     * 장바구니 조회
     * @param cartId
     * @return
     */
    public List<CartItem> getCartItemList(Long cartId) {
        return jpaQueryFactory
                .selectFrom(cartItem)
                .leftJoin(cartItem.cart, cart)
                .fetchJoin()
                .distinct()
                .where(cart.id.eq(cartId))
                .fetch();
    }


}
