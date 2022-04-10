package com.example.JpaVueShop_backend.service.api;

import com.example.JpaVueShop_backend.config.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final JwtService jwtService;

    /**
     * 마이페이지 유저 정보 가져오기
     * @param request
     * @return
     */
    public Long getUserData(HttpServletRequest request) {
        String accessToken = request.getHeader("accessToken");
        Long userId = jwtService.getUserId(accessToken);

        return userId;
    }
}
