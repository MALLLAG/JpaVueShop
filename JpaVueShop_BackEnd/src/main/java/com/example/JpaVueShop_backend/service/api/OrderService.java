package com.example.JpaVueShop_backend.service.api;

import com.example.JpaVueShop_backend.domain.item.Item;
import com.example.JpaVueShop_backend.domain.item.ItemRepo;
import com.example.JpaVueShop_backend.domain.item.ItemRepoSup;
import com.example.JpaVueShop_backend.domain.order.Order;
import com.example.JpaVueShop_backend.domain.order.OrderRepo;
import com.example.JpaVueShop_backend.domain.order.OrderRepoSup;
import com.example.JpaVueShop_backend.domain.orderItem.OrderItem;
import com.example.JpaVueShop_backend.domain.user.User;
import com.example.JpaVueShop_backend.domain.user.UserRepoSup;
import com.example.JpaVueShop_backend.domain.userCoupon.UserCoupon;
import com.example.JpaVueShop_backend.domain.userCoupon.UserCouponRepo;
import com.example.JpaVueShop_backend.domain.userCoupon.UserCouponRepoSup;
import com.example.JpaVueShop_backend.dto.api.order.OrderDto;
import com.example.JpaVueShop_backend.handler.exeption.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ItemRepo itemRepo;
    private final OrderRepo orderRepo;
    private final UserCouponRepo userCouponRepo;

    /**
     * 장바구니 상품들 주문
     * @param orderDtoList
     * @param user
     */
    @Transactional
    public void orders(List<OrderDto> orderDtoList, Long userCouponId, int usedPoint, User user) {
        UserCoupon userCoupon = null;
        if (userCouponId != null) {
            userCoupon = userCouponRepo.findById(userCouponId).<CustomApiException>orElseThrow(() -> {
                throw new CustomApiException("해당 쿠폰을 찾지 못했습니다.");
            });
            userCoupon.setIsUsed("Y");
        }

        int currentPoint = user.getPoint();

        if (currentPoint < usedPoint) {
            throw new CustomApiException("사용할 수 있는 포인트가 부족합니다");
        }

        int remainPoint = currentPoint - usedPoint;
        user.setPoint(remainPoint);

        List<OrderItem> orderItemList = new ArrayList<>();
        for (OrderDto orderDto : orderDtoList) {
            Item item = itemRepo.findById(orderDto.getItemId()).<CustomApiException>orElseThrow(() -> {
                throw new CustomApiException("해당 상품을 찾지 못했습니다.");
            });

            OrderItem orderItem = OrderItem.createOrderItem(item);
            orderItemList.add(orderItem);
        }

        Order order = Order.createOrder(user, orderItemList, userCoupon, usedPoint);
        orderRepo.save(order);
    }

}
