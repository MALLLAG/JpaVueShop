package com.example.JpaVueShop_backend.config;

import com.example.JpaVueShop_backend.config.jwt.JwtService;
import com.example.JpaVueShop_backend.domain.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class JwtServiceTest {

    @Autowired
    private JwtService jwtService;

    @Test
    @Transactional
    @DisplayName("JWT AccessToken 생성 테스트")
    public void createAccessToken() {
        Map<String, Object> userData = new HashMap<>();
        userData.put("username", "testtest20");
        userData.put("ROLE", Role.USER);

        String accessToken = jwtService.createAccessToken(1L + "", 1000 * 60 * 10, userData);

        assertEquals(1L, jwtService.getUserId(accessToken));
    }

}
