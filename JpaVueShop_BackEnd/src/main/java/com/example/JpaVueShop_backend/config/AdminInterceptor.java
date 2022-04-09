package com.example.JpaVueShop_backend.config;

import com.example.JpaVueShop_backend.config.jwt.JwtService;
import com.example.JpaVueShop_backend.handler.exeption.RefreshTokenExpired;
import com.example.JpaVueShop_backend.handler.exeption.UnauthorizedException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AdminInterceptor implements HandlerInterceptor {

    private static final String ACCESS_TOKEN = "accessToken";

    private final JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String accessToken = request.getHeader(ACCESS_TOKEN);

        // accessToken 없을 시 내보냄
        if (accessToken == null) {
            return false;
        }

        Claims claims = jwtService.getUserData(accessToken);
        Map<String, String> userData = (Map<String, String>) claims.get("userData");

        // 권한이 ADMIN 아닐 시 내보냄
        if (!userData.get("ROLE").equals("ADMIN")) {
            return false;
        }

        String refreshToken = "";

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refreshToken")) {
                refreshToken = cookie.getValue();
            }
        }

        if (accessToken != null && jwtService.isUsable(accessToken)) {

            // accessToken이 만료되었을경우
            if (!jwtService.isExpire(accessToken)) {
                throw new UnauthorizedException();
            }

            // refreshToken이 만료되었을경우
            if (!jwtService.isExpire(refreshToken)) {
                throw new RefreshTokenExpired();
            }

            return true;
        } else {
            throw new UnauthorizedException();
        }

    }
}
