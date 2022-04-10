package com.example.JpaVueShop_backend.controller.api;

import com.example.JpaVueShop_backend.config.jwt.JwtService;
import com.example.JpaVueShop_backend.dto.CMRespDto;
import com.example.JpaVueShop_backend.service.api.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/myPage")
public class MyPageController {

    private final MyPageService myPageService;

    /**
     * 마이페이지 유저 정보 가져오기
     * @param request
     * @return
     */
    @GetMapping("/getUserData")
    public CMRespDto<?> getUserData(HttpServletRequest request) {
        return new CMRespDto<>(1, "회원정보 가져오기 성공", myPageService.getUserData(request));
    }
}
