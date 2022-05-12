package com.example.JpaVueShop_backend.service.api;

import com.example.JpaVueShop_backend.config.jwt.JwtService;
import com.example.JpaVueShop_backend.domain.Role;
import com.example.JpaVueShop_backend.domain.user.User;
import com.example.JpaVueShop_backend.domain.user.UserRepo;
import com.example.JpaVueShop_backend.domain.user.UserRepoSup;
import com.example.JpaVueShop_backend.dto.api.user.JoinReqDto;
import com.example.JpaVueShop_backend.dto.api.user.LoginReqDto;
import com.example.JpaVueShop_backend.handler.exeption.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final String ACCESS_TOKEN = "accessToken";
    private static final String REFRESH_TOKEN = "refreshToken";
    private static final int TEN_MINUTE = 1000 * 60 * 10;
    private static final int A_WEEK = 1000 * 60 * 60 * 24 * 7;

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;

    /**
     * accessToken 만료 시 재발급
     * @param request
     * @param response
     */
    @Transactional
    public void createToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = "";

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(REFRESH_TOKEN)) {
                refreshToken = cookie.getValue();
            }
        }

        User user = userRepo.findByRefreshToken(refreshToken).<CustomApiException>orElseThrow(() -> {
            throw new CustomApiException("잘못된 접근입니다.\n다시 로그인해주세요.");
        });

        Map<String, Object> userData = new HashMap<>();
        userData.put("username", user.getUsername());
        userData.put("ROLE", user.getROLE());

        String accessToken = jwtService.createAccessToken(user.getId()+"", TEN_MINUTE, userData);
        response.setHeader(ACCESS_TOKEN, accessToken);

    }

    /**
     * 로그인
     * @param loginReqDto
     * @param response
     * @return
     */
    @Transactional
    public Long login(LoginReqDto loginReqDto, HttpServletResponse response) {
        Map<String, Object> userData = new HashMap<>();

        User user = userRepo.findByUsername(loginReqDto.getUsername());
        if (user == null) {
            throw new CustomApiException("존재하지 않는 아이디입니다.");
        }

        if (bCryptPasswordEncoder.matches(loginReqDto.getPassword(), user.getPassword())) {
            userData.put("username", user.getUsername());
            userData.put("ROLE", user.getROLE());
        } else {
            throw new CustomApiException("잘못된 비밀번호입니다.");
        }

        Long userId = user.getId();

        String accessToken = jwtService.createAccessToken(userId + "", TEN_MINUTE, userData);
        String refreshToken = jwtService.createRefreshToken(A_WEEK);
        response.setHeader(ACCESS_TOKEN, accessToken);

        Cookie cookie = new Cookie(REFRESH_TOKEN, refreshToken);
        cookie.setMaxAge(A_WEEK);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);

        user.setRefreshToken(refreshToken);

        return userId;
    }

    /**
     * 회원가입
     * @param joinReqDto
     * @return
     */
    @Transactional
    public Long join(JoinReqDto joinReqDto) {
        if (userRepo.existsByUsername(joinReqDto.getUsername())) {
            throw new CustomApiException("해당 아이디는 이미 사용중입니다.");
        }

        if (joinReqDto.getUsername().equals("admin")) {
            joinReqDto.setROLE(Role.ADMIN);
        }

        User user = User.createUser(joinReqDto);
        userRepo.save(user);

        return user.getId();
    }
}
