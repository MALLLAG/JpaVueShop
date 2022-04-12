package com.example.JpaVueShop_backend.controller.api;

import com.example.JpaVueShop_backend.dto.CMRespDto;
import com.example.JpaVueShop_backend.dto.api.myPage.PasswordModifyDto;
import com.example.JpaVueShop_backend.dto.api.myPage.PasswordValidationDto;
import com.example.JpaVueShop_backend.service.api.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/myPage")
public class MyPageController {

    private final MyPageService myPageService;

    /**
     * 비밀번호 변경
     * @param passwordModifyDto
     * @return
     */
    @PostMapping("/passwordModify")
    public CMRespDto<?> passwordModify(@Valid @RequestBody PasswordModifyDto passwordModifyDto,
                                       BindingResult bindingResult,
                                       HttpServletRequest request) {
        myPageService.passwordModify(passwordModifyDto, request);
        return new CMRespDto<>(1, "비밀번호 변경 완료", null);
    }

    /**
     * 비밀번호 변경 시 현재 비밀번호 확인
     * @return
     */
    @PostMapping("/currentPasswordCheck")
    public CMRespDto<?> currentPasswordCheck(@Valid @RequestBody PasswordValidationDto passwordValidationDto,
                                             BindingResult bindingResult,
                                             HttpServletRequest request) {
        return new CMRespDto<>(1, "비밀번호 확인 완료", myPageService.currentPasswordCheck(passwordValidationDto, request));
    }

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
