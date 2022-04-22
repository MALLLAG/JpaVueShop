package com.example.JpaVueShop_backend.domain.category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long> {
    Category findByName(String name);
}
