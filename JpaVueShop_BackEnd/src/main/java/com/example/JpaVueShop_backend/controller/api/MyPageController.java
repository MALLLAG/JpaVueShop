package com.example.JpaVueShop_backend.controller.api;

import com.example.JpaVueShop_backend.dto.CMRespDto;
import com.example.JpaVueShop_backend.dto.PageDto;
import com.example.JpaVueShop_backend.dto.api.myPage.PasswordModifyDto;
import com.example.JpaVueShop_backend.dto.api.myPage.PasswordValidationDto;
import com.example.JpaVueShop_backend.dto.api.myPage.RegisterCouponDto;
import com.example.JpaVueShop_backend.dto.api.order.OrderIdDto;
import com.example.JpaVueShop_backend.service.api.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
     * 쿠폰 등록
     * @param registerCouponDto
     * @param request
     * @return
     */
    @PostMapping("/registerCoupon")
    public CMRespDto<?> registerCoupon(@RequestBody RegisterCouponDto registerCouponDto, HttpServletRequest request) {
        myPageService.registerCoupon(registerCouponDto, request);
        return new CMRespDto<>(1, "쿠폰 등록 완료", null);
    }

    /**
     * 상세 주문 페이지 입장 전 주문한 유저가 맞는지 체크
     * @param orderIdDto
     * @param request
     * @return
     */
    @PostMapping("/checkUser")
    public CMRespDto<?> checkUser(@RequestBody OrderIdDto orderIdDto, HttpServletRequest request) {
        myPageService.checkUser(orderIdDto, request);
        return new CMRespDto<>(1, "유저 체크 완료", null);
    }

    /**
     * 상세 주문
     * @param orderIdDto
     * @param request
     * @return
     */
    @PostMapping("/getOrderDetail")
    public CMRespDto<?> getOrderDetail(@RequestBody OrderIdDto orderIdDto, HttpServletRequest request) {
        return new CMRespDto<>(1, "상세 주문 가져오기 완료", myPageService.getOrderDetail(orderIdDto, request));
    }

    /**
     * 주문 내역
     * @param pageDto
     * @param request
     * @return
     */
    @PostMapping("/getOrderList")
    public CMRespDto<?> getOrderList(@RequestBody PageDto pageDto, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(pageDto.getCurrentPage(), 10);
        return new CMRespDto<>(1, "주문 내역 가져오기 완료", myPageService.getOrderList(request, pageable));
    }

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
     * 마이페이지 쿠폰/포인트
     * @param request
     * @return
     */
    @GetMapping("/getCouponPoint")
    public CMRespDto<?> getCouponPoint(HttpServletRequest request) {
        return new CMRespDto<>(1, "쿠폰/포인트 데이터 가져오기 완료", myPageService.getCouponPoint(request));
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
