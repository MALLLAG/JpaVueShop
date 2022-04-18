package com.example.JpaVueShop_backend.domain.item;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminItemRepo extends JpaRepository<Item, Long> {
}
