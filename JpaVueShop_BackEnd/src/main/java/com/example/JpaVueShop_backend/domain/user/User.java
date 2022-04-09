package com.example.JpaVueShop_backend.domain.user;

import com.example.JpaVueShop_backend.domain.order.Order;
import com.example.JpaVueShop_backend.domain.Role;
import com.example.JpaVueShop_backend.domain.userCoupon.UserCoupon;
import com.example.JpaVueShop_backend.dto.api.JoinReqDto;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

    /**
     * 유저 생성
     * @param joinReqDto
     * @return
     */
    public static User createUser(JoinReqDto joinReqDto) {

        User user = new User();

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String rawPassword = joinReqDto.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        user.setUsername(joinReqDto.getUsername());
        user.setPassword(encPassword);
        user.setROLE(joinReqDto.getROLE());

        return user;
    }
}
