package com.example.JpaVueShop_backend.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itemId")
    private Long id;

    private String thumbnail;
    private int price;
    private String title;
    private String isAccept;
    private LocalDateTime regDate;

    @JoinColumn(name = "userId")
    @ManyToOne()
    private User user;

    @PrePersist
    public void regDate() {
        this.regDate = LocalDateTime.now();
    }
}
