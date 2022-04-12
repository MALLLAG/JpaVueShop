package com.example.JpaVueShop_backend.service.api;

import com.example.JpaVueShop_backend.config.jwt.JwtService;
import com.example.JpaVueShop_backend.domain.user.User;
import com.example.JpaVueShop_backend.domain.user.UserRepo;
import com.example.JpaVueShop_backend.dto.api.myPage.PasswordModifyDto;
import com.example.JpaVueShop_backend.dto.api.myPage.PasswordValidationDto;
import com.example.JpaVueShop_backend.dto.api.myPage.UserDataRespDto;
import com.example.JpaVueShop_backend.handler.exeption.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;
    private final UserRepo userRepo;

    /**
     * 비밀번호 변경
     * @param passwordModifyDto
     */
    @Transactional
    public void passwordModify(PasswordModifyDto passwordModifyDto, HttpServletRequest request) {
        String token = request.getHeader("accessToken");
        Long userId = jwtService.getUserId(token);
        String rawPassword = passwordModifyDto.getPassword();

        User user = userRepo.findById(userId).<CustomApiException>orElseThrow(() -> {
            throw new CustomApiException("해당 유저를 찾을 수 없습니다.");
        });

        if (bCryptPasswordEncoder.matches(rawPassword, user.getPassword())) {
            throw new CustomApiException("변경하려는 비밀번호가 현재 비밀번호와 같습니다.");
        }

        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
    }

    /**
     * 비밀번호 변경 시 현재 비밀번호 확인
     * @return
     */
    @Transactional(readOnly = true)
    public boolean currentPasswordCheck(PasswordValidationDto passwordValidationDto, HttpServletRequest request) {
        String token = request.getHeader("accessToken");
        Long userId = jwtService.getUserId(token);
        String currentPassword = passwordValidationDto.getCurrentPassword();

        User user = userRepo.findById(userId).<CustomApiException>orElseThrow(() -> {
            throw new CustomApiException("해당 유저를 찾을 수 없습니다.");
        });

        return bCryptPasswordEncoder.matches(currentPassword, user.getPassword()) ? true : false;
    }


    /**
     * 마이페이지 유저 정보 가져오기
     * @param request
     * @return
     */
    @Transactional(readOnly = true)
    public UserDataRespDto getUserData(HttpServletRequest request) {
        String accessToken = request.getHeader("accessToken");
        Long userId = jwtService.getUserId(accessToken);

        User user = userRepo.findById(userId).<CustomApiException>orElseThrow(() -> {
            throw new CustomApiException("해당 유저를 찾지 못했습니다.");
        });

        UserDataRespDto userDataRespDto = new UserDataRespDto(user);
        return userDataRespDto;
    }
}
