package com.example.JpaVueShop_backend.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "couponId")
    private Long id;
    private String couponName;
    private String couponNumber;

    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date endDate;

    private int amount;
    private LocalDateTime regDate;

    @PrePersist
    public void regDate() {
        this.regDate = LocalDateTime.now();
    }
}
