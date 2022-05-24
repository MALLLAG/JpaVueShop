package com.example.JpaVueShop_backend.api;

import com.example.JpaVueShop_backend.domain.Role;
import com.example.JpaVueShop_backend.domain.user.User;
import com.example.JpaVueShop_backend.domain.user.UserRepo;
import com.example.JpaVueShop_backend.dto.api.user.JoinReqDto;
import com.example.JpaVueShop_backend.dto.api.user.LoginReqDto;
import com.example.JpaVueShop_backend.handler.exeption.CustomApiException;
import com.example.JpaVueShop_backend.service.api.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private HttpServletResponse response;

    @Test
    @Transactional
    public void 로그인_테스트() {
        중복_아이디_체크();

        LoginReqDto loginReqDto = new LoginReqDto();
        loginReqDto.setUsername("testtest20");
        loginReqDto.setPassword("Test1234123!");

        User findUser = userRepo.findByUsername(loginReqDto.getUsername());

        Long loginUserId = userService.login(loginReqDto, response);

        assertEquals(findUser.getId(), loginUserId);
    }

    @Test
    @Transactional
    public void 중복_아이디_체크() {
        JoinReqDto joinReqDto = new JoinReqDto();
        joinReqDto.setUsername("testtest20");
        joinReqDto.setPassword("Test1234123!");
        joinReqDto.setROLE(Role.USER);

        JoinReqDto joinReqDto2 = new JoinReqDto();
        joinReqDto2.setUsername("testtest20");
        joinReqDto2.setPassword("Test1234123!");
        joinReqDto2.setROLE(Role.USER);

        userService.join(joinReqDto);

        CustomApiException e = assertThrows(CustomApiException.class, () -> userService.join(joinReqDto2));
        assertEquals(e.getMessage(), "해당 아이디는 이미 사용중입니다.");
    }

    @Test
    @Transactional
    public void 회원가입_테스트() {
        JoinReqDto joinReqDto = new JoinReqDto();
        joinReqDto.setUsername("testtest20");
        joinReqDto.setPassword("Test1234123!");
        joinReqDto.setROLE(Role.USER);

        Long userId = userService.join(joinReqDto);

        User user = userRepo.findById(userId).<CustomApiException>orElseThrow(() -> {
            throw new CustomApiException("해당 유저를 찾지 못했습니다.");
        });

        assertEquals(userId, user.getId());
        assertEquals(joinReqDto.getUsername(), user.getUsername());
    }
}
