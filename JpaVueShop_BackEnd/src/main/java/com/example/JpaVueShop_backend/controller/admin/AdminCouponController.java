package com.example.JpaVueShop_backend.controller.admin;

import com.example.JpaVueShop_backend.dto.CMRespDto;
import com.example.JpaVueShop_backend.dto.PageDto;
import com.example.JpaVueShop_backend.dto.admin.coupon.PublishCouponDto;
import com.example.JpaVueShop_backend.service.admin.AdminCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/coupon")
public class AdminCouponController {

    private final AdminCouponService adminCouponService;

    /**
     * 쿠폰 발행
     * @param publishCouponDto
     * @return
     */
    @PostMapping("/publishCoupon")
    public CMRespDto<?> publishCoupon(@RequestBody PublishCouponDto publishCouponDto) {
        adminCouponService.publishCoupon(publishCouponDto);
        return new CMRespDto<>(1, "쿠폰 발행 완료", null);
    }

    /**
     * 쿠폰 리스트
     * @param pageDto
     * @return
     */
    @PostMapping("/getCouponList")
    public CMRespDto<?> getCouponList(@RequestBody PageDto pageDto) {
        Pageable pageable = PageRequest.of(pageDto.getCurrentPage(), 10);
        return new CMRespDto<>(1, "쿠폰 리스트 가져오기 완료", adminCouponService.getCouponList(pageable));
    }
}
