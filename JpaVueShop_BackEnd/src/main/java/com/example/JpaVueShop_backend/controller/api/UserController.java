package com.example.JpaVueShop_backend.controller.api;

import com.example.JpaVueShop_backend.dto.CMRespDto;
import com.example.JpaVueShop_backend.dto.api.user.JoinReqDto;
import com.example.JpaVueShop_backend.dto.api.user.LoginReqDto;
import com.example.JpaVueShop_backend.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    /**
     * 로그인
     * @param loginReqDto
     * @param response
     * @return
     */
    @PostMapping("/login")
    public CMRespDto<?> login(@RequestBody LoginReqDto loginReqDto, HttpServletResponse response) {
        return new CMRespDto<>(1, "로그인이 완료되었습니다", userService.login(loginReqDto, response));
    }

    /**
     * 회원가입
     * @param joinReqDto
     * @param bindingResult
     * @return
     */
    @PostMapping("/join")
    public CMRespDto<?> join(@Valid @RequestBody JoinReqDto joinReqDto, BindingResult bindingResult) {
        return new CMRespDto<>(1, "회원가입이 완료되었습니다.", userService.join(joinReqDto));
    }
}
