package com.example.JpaVueShop_backend.controller.admin;

import com.example.JpaVueShop_backend.dto.CMRespDto;
import com.example.JpaVueShop_backend.dto.PageDto;
import com.example.JpaVueShop_backend.service.admin.AdminOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/order")
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    /**
     * 전체 주문 내역 가져오기
     * @param pageDto
     * @return
     */
    @PostMapping("/getOrderList")
    public CMRespDto<?> getOrderList(@RequestBody PageDto pageDto) {
        Pageable pageable = PageRequest.of(pageDto.getCurrentPage(), 10);
        return new CMRespDto<>(1, "주문 내역 가져오기 완료", adminOrderService.getOrderList(pageable));
    }
}
