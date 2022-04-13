package com.example.JpaVueShop_backend.admin.coupon;

import com.example.JpaVueShop_backend.dto.admin.coupon.PublishCouponDto;
import com.example.JpaVueShop_backend.handler.exeption.CustomApiException;
import com.example.JpaVueShop_backend.service.admin.AdminCouponService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class AdminCouponServiceTest {

    @Autowired
    private AdminCouponService adminCouponService;

    @Test
    @Transactional
    @DisplayName("중복 쿠폰 발행 테스트")
    public void couponDuplicateCheck() {
        Date date = new Date();

        PublishCouponDto publishCouponDto1 = new PublishCouponDto();
        publishCouponDto1.setCouponName("test");
        publishCouponDto1.setCouponNumber("test");
        publishCouponDto1.setAmount(10000);
        publishCouponDto1.setStartDate(date);
        publishCouponDto1.setEndDate(date);

        PublishCouponDto publishCouponDto2 = new PublishCouponDto();
        publishCouponDto2.setCouponName("test");
        publishCouponDto2.setCouponNumber("test");
        publishCouponDto2.setAmount(10000);
        publishCouponDto2.setStartDate(date);
        publishCouponDto2.setEndDate(date);

        adminCouponService.publishCoupon(publishCouponDto1);

        CustomApiException e = assertThrows(CustomApiException.class, () -> adminCouponService.publishCoupon(publishCouponDto2));
        assertEquals(e.getMessage(), "이미 존재하는 쿠폰입니다.");
    }


    @Test
    @Transactional
    @DisplayName("쿠폰 발행 테스트")
    public void publishCoupon() {
        Date date = new Date();

        PublishCouponDto publishCouponDto = new PublishCouponDto();
        publishCouponDto.setCouponName("test");
        publishCouponDto.setCouponNumber("test");
        publishCouponDto.setAmount(10000);
        publishCouponDto.setStartDate(date);
        publishCouponDto.setEndDate(date);

        adminCouponService.publishCoupon(publishCouponDto);
    }

}
