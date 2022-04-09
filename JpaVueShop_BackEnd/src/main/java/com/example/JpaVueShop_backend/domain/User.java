package com.example.JpaVueShop_backend.domain;

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
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private int point;
    private String refreshToken;

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserCoupon> userCoupons = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role ROLE;

    private LocalDateTime regDate;

    @PrePersist
    public void regDate() {
        this.regDate = LocalDateTime.now();
    }
}
