package com.example.JpaVueShop_backend.service.admin;

import com.example.JpaVueShop_backend.domain.order.AdminOrderRepo;
import com.example.JpaVueShop_backend.domain.order.AdminOrderRepoSup;
import com.example.JpaVueShop_backend.domain.order.Order;
import com.example.JpaVueShop_backend.dto.admin.order.OrderRespDto;
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
public class AdminOrderService {

    private final AdminOrderRepo adminOrderRepo;
    private final AdminOrderRepoSup adminOrderRepoSup;

    /**
     * 전체 주문 내역 가져오기
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public Page<OrderRespDto> getOrderList(Pageable pageable) {
        List<Order> orderList = adminOrderRepo.findAllByOrderByIdDesc(pageable);
        List<OrderRespDto> orderRespDtoList = new ArrayList<>();
        Long totalCount = adminOrderRepo.count();

        for (Order order : orderList) {
            OrderRespDto orderRespDto = new OrderRespDto(order);
            orderRespDtoList.add(orderRespDto);
        }

        return new PageImpl<>(orderRespDtoList, pageable, totalCount);
    }
}
