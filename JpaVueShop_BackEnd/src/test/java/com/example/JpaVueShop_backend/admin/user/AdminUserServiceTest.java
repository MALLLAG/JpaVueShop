package com.example.JpaVueShop_backend.admin.user;

import com.example.JpaVueShop_backend.dto.admin.user.UserRespDto;
import com.example.JpaVueShop_backend.service.admin.AdminUserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AdminUserServiceTest {

    @Autowired
    private AdminUserService adminUserService;

    @Test
    @Transactional
    @DisplayName("가입한 유저 리스트 테스트")
    public void login() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<UserRespDto> userRespDtoList = adminUserService.getUserList(pageable);

        assertEquals(userRespDtoList.getContent().get(0).getUsername(), "beyool95");
    }

}
