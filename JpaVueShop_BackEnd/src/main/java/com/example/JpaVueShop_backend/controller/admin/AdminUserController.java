package com.example.JpaVueShop_backend.controller.admin;

import com.example.JpaVueShop_backend.dto.CMRespDto;
import com.example.JpaVueShop_backend.dto.PageDto;
import com.example.JpaVueShop_backend.service.admin.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/user")
public class AdminUserController {

    private final AdminUserService adminUserService;

    /**
     * 가입한 유저 리스트
     * @return
     */
    @PostMapping("/getUserList")
    public CMRespDto<?> getUserList(@RequestBody PageDto pageDto) {
        Pageable pageable = PageRequest.of(pageDto.getCurrentPage(), 10);
        return new CMRespDto<>(1, "유저 목록 가져오기 완료", adminUserService.getUserList(pageable));
    }
}
