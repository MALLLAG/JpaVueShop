package com.example.JpaVueShop_backend.service.admin;

import com.example.JpaVueShop_backend.domain.coupon.AdminCouponRepo;
import com.example.JpaVueShop_backend.domain.coupon.AdminCouponRepoSup;
import com.example.JpaVueShop_backend.domain.coupon.Coupon;
import com.example.JpaVueShop_backend.dto.admin.coupon.CouponRespDto;
import com.example.JpaVueShop_backend.dto.admin.coupon.PublishCouponDto;
import com.example.JpaVueShop_backend.handler.exeption.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCouponService {

    private final AdminCouponRepo adminCouponRepo;
    private final AdminCouponRepoSup adminCouponRepoSup;

    /**
     * 쿠폰 발행
     * @param publishCouponDto
     */
    @Transactional
    public void publishCoupon(PublishCouponDto publishCouponDto) {
        String couponNumber = publishCouponDto.getCouponNumber();

        Coupon couponDuplicateCheck = adminCouponRepo.findByCouponNumber(couponNumber);
        if (couponDuplicateCheck != null) {
            throw new CustomApiException("이미 존재하는 쿠폰입니다.");
        }

        Coupon coupon = Coupon.createCoupon(publishCouponDto);
        adminCouponRepo.save(coupon);
    }

    /**
     * 쿠폰 리스트
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public Page<CouponRespDto> getCouponList(Pageable pageable) {
        List<Coupon> couponList = adminCouponRepo.findAllByOrderByIdDesc(pageable);
        List<CouponRespDto> couponRespDtoList = new ArrayList<>();
        Long totalCount = adminCouponRepo.count();

        for (Coupon coupon : couponList) {
            CouponRespDto couponDto = new CouponRespDto(coupon);
            couponRespDtoList.add(couponDto);
        }

        return new PageImpl<>(couponRespDtoList, pageable, totalCount);
    }
}
