package com.example.JpaVueShop_backend.domain.order;

import com.example.JpaVueShop_backend.domain.orderItem.OrderItem;
import com.example.JpaVueShop_backend.domain.OrderStatus;
import com.example.JpaVueShop_backend.domain.review.Review;
import com.example.JpaVueShop_backend.domain.user.User;
import com.example.JpaVueShop_backend.domain.userCoupon.UserCoupon;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderId")
    private Long id;

    @JoinColumn(name = "userId")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private String orderTitle;
    private int couponAmount;
    private int usedPoint;
    private int originalPrice;
    private int paymentPrice;
    private int serviceFee;
    private int paymentFee;
    private LocalDateTime regDate;

    @PrePersist
    public void regDate() {
        this.regDate = LocalDateTime.now();
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    /**
     * 주문하는 상품들의 가격 총 합
     * @param orderItemList
     * @return
     */
    public static int getOriginalPrice(List<OrderItem> orderItemList) {
        int originalPrice = 0;
        for (OrderItem orderItem : orderItemList) {
            originalPrice += orderItem.getOrderPrice();
        }

        return originalPrice;
    }

    /**
     * 주문 생성
     * @param user
     * @param orderItemList
     * @return
     */
    public static Order createOrder(User user, List<OrderItem> orderItemList, UserCoupon userCoupon, int usedPoint) {
        Order order = new Order();
        order.setUser(user);

        for (OrderItem orderItem : orderItemList) {
            order.addOrderItem(orderItem);
        }

        int originalPrice = Order.getOriginalPrice(orderItemList);

        int couponAmount = 0;
        if (userCoupon != null) {
            couponAmount = userCoupon.getCoupon().getAmount();
        }

        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderTitle(user.getUsername() + "님의 주문서입니다.");
        order.setOriginalPrice(originalPrice);
        order.setCouponAmount(couponAmount);
        order.setUsedPoint(usedPoint);
        order.setPaymentPrice(originalPrice - usedPoint - couponAmount);

        return order;
    }
}
