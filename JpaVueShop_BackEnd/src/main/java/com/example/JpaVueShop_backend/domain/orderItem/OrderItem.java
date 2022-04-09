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

    private int orderPrice;
    private LocalDateTime regDate;

    @PrePersist
    public void regDateAndIsPaymentReq() {
        this.regDate = LocalDateTime.now();
    }
}
