package com.example.JpaVueShop_backend.api;

import com.example.JpaVueShop_backend.config.jwt.JwtService;
import com.example.JpaVueShop_backend.domain.Role;
import com.example.JpaVueShop_backend.domain.user.User;
import com.example.JpaVueShop_backend.domain.user.UserRepo;
import com.example.JpaVueShop_backend.dto.api.myPage.UserDataRespDto;
import com.example.JpaVueShop_backend.dto.api.user.JoinReqDto;
import com.example.JpaVueShop_backend.handler.exeption.CustomApiException;
import com.example.JpaVueShop_backend.service.api.MyPageService;
import com.example.JpaVueShop_backend.service.api.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MyPageServiceTest {

    private static final String ACCESS_TOKEN = "accessToken";
    private static final int TEN_MINUTE = 1000 * 60 * 10;
    private static final int A_WEEK = 1000 * 60 * 60 * 24 * 7;
    MockHttpServletRequest request = new MockHttpServletRequest();

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MyPageService myPageService;

    @BeforeEach
    public void init() {
        JoinReqDto joinReqDto = new JoinReqDto();
        joinReqDto.setUsername("testtest20");
        joinReqDto.setPassword("Test1234123!");
        joinReqDto.setROLE(Role.USER);

        Long userId = userService.join(joinReqDto);

        User user = userRepo.findById(userId).<CustomApiException>orElseThrow(() -> {
            throw new CustomApiException("해당 유저를 찾지 못했습니다.");
        });

        Map<String, Object> userData = new HashMap<>();
        userData.put("username", user.getUsername());
        userData.put("ROLE", user.getROLE());

        String accessToken = jwtService.createAccessToken(user.getId()+"", TEN_MINUTE, userData);
        request.addHeader(ACCESS_TOKEN, accessToken);
    }

    @Test
    @Transactional
    public void 마이페이지_유저정보_가져오기() {
        UserDataRespDto userDataRespDto = myPageService.getUserData(request);

        assertEquals(userDataRespDto.getUsername(), "testtest20");
        assertEquals(userDataRespDto.getROLE(), Role.USER);
    }
}
