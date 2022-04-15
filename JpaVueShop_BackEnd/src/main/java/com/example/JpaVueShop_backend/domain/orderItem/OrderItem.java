package com.example.JpaVueShop_backend.domain.orderItem;

import com.example.JpaVueShop_backend.domain.item.Item;
import com.example.JpaVueShop_backend.domain.order.Order;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Table(name = "orderitem")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderItemId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId")
    private Order order;

    private Long makerUserId;
    private int orderPrice;
    private LocalDateTime regDate;

    @PrePersist
    public void regDateAndIsPaymentReq() {
        this.regDate = LocalDateTime.now();
    }


    /**
     * orderItem 생성
     * @param item
     * @return
     */
    public static OrderItem createOrderItem(Item item) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setMakerUserId(item.getUser().getId());

        // 할인된 가격으로 생성
        int price = (int) (item.getPrice() * ((100.0 - item.getDiscountRate()) / 100.0));
        orderItem.setOrderPrice(price);

        return orderItem;
    }

}
