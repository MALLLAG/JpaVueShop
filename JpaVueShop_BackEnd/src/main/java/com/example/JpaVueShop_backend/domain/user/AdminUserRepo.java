package com.example.JpaVueShop_backend.domain.user;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminUserRepo extends JpaRepository<User, Long> {
    List<User> findAllByOrderByIdDesc(Pageable pageable);
}
