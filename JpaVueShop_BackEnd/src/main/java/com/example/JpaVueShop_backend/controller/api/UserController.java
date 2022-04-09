package com.example.JpaVueShop_backend.controller.api;

import com.example.JpaVueShop_backend.dto.CMRespDto;
import com.example.JpaVueShop_backend.dto.api.JoinReqDto;
import com.example.JpaVueShop_backend.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    /**
     * 회원가입
     * @param joinReqDto
     * @param bindingResult
     * @return
     */
    @PostMapping("/join")
    public CMRespDto<?> join(@RequestBody JoinReqDto joinReqDto, BindingResult bindingResult) {
        return new CMRespDto<>(1, "회원가입이 완료되었습니다.", userService.join(joinReqDto));
    }
}
