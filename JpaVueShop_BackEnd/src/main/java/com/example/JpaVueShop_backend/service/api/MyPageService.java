package com.example.JpaVueShop_backend.service.api;

import com.example.JpaVueShop_backend.config.jwt.JwtService;
import com.example.JpaVueShop_backend.domain.coupon.Coupon;
import com.example.JpaVueShop_backend.domain.coupon.CouponRepo;
import com.example.JpaVueShop_backend.domain.order.Order;
import com.example.JpaVueShop_backend.domain.order.OrderRepo;
import com.example.JpaVueShop_backend.domain.order.OrderRepoSup;
import com.example.JpaVueShop_backend.domain.orderItem.OrderItem;
import com.example.JpaVueShop_backend.domain.review.Review;
import com.example.JpaVueShop_backend.domain.review.ReviewRepo;
import com.example.JpaVueShop_backend.domain.user.User;
import com.example.JpaVueShop_backend.domain.user.UserRepo;
import com.example.JpaVueShop_backend.domain.userCoupon.UserCoupon;
import com.example.JpaVueShop_backend.domain.userCoupon.UserCouponRepo;
import com.example.JpaVueShop_backend.domain.userCoupon.UserCouponRepoSup;
import com.example.JpaVueShop_backend.dto.api.myPage.*;
import com.example.JpaVueShop_backend.dto.api.order.OrderIdDto;
import com.example.JpaVueShop_backend.dto.api.order.OrderItemDto;
import com.example.JpaVueShop_backend.handler.exeption.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;
    private final UserRepo userRepo;
    private final UserCouponRepo userCouponRepo;
    private final UserCouponRepoSup userCouponRepoSup;
    private final OrderRepo orderRepo;
    private final ReviewRepo reviewRepo;
    private final CouponRepo couponRepo;

    /**
     * ?????? ??????
     * @param registerCouponDto
     * @param request
     */
    @Transactional
    public void registerCoupon(RegisterCouponDto registerCouponDto, HttpServletRequest request) {
        String couponNumber = registerCouponDto.getCouponNumber();
        String token = request.getHeader("accessToken");
        Long userId = jwtService.getUserId(token);

        User user = userRepo.findById(userId).<CustomApiException>orElseThrow(() -> {
            throw new CustomApiException("?????? ????????? ?????? ???????????????.");
        });

        Coupon coupon = couponRepo.findByCouponNumber(couponNumber).<CustomApiException>orElseThrow(() -> {
            throw new CustomApiException("???????????? ?????? ???????????????.");
        });

        String startDateToString = String.valueOf(coupon.getStartDate()).substring(0, 10);
        String endDateToString = String.valueOf(coupon.getEndDate()).substring(0, 10);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.KOREA);
        LocalDate startDate = LocalDate.parse(startDateToString, formatter);
        LocalDate endDate = LocalDate.parse(endDateToString, formatter);
        LocalDate now = LocalDate.now();

        // startDate ????????? ????????? ???
        if (now.compareTo(startDate) < 0) {
            throw new CustomApiException("???????????? ?????? ???????????????.");
        }
        // endDate ????????? ????????? ???
        if (now.compareTo(endDate) > 0) {
            throw new CustomApiException("??????????????? ?????? ???????????????.");
        }

        UserCoupon userCouponDuplicateCheck = userCouponRepo.findByCouponId(coupon.getId());
        if (userCouponDuplicateCheck != null) {
            throw new CustomApiException("?????? ???????????? ???????????????.");
        }

        UserCoupon userCoupon = UserCoupon.createUserCoupon(user, coupon);
        userCouponRepo.save(userCoupon);
    }

    /**
     * ?????? ?????? ????????? ?????? ??? ????????? ????????? ????????? ??????
     * @param orderIdDto
     * @param request
     */
    @Transactional(readOnly = true)
    public void checkUser(OrderIdDto orderIdDto, HttpServletRequest request) {
        String token = request.getHeader("accessToken");
        Long userId = jwtService.getUserId(token);
        Long orderId = orderIdDto.getOrderId();

        orderRepo.findByIdAndUserId(orderId, userId).<CustomApiException>orElseThrow(() -> {
            throw new CustomApiException("????????? ??? ?????? ????????? ????????????.");
        });
    }

    /**
     * ?????? ??????
     * @param orderIdDto
     * @param request
     * @return
     */
    @Transactional(readOnly = true)
    public OrderDetailDto getOrderDetail(OrderIdDto orderIdDto, HttpServletRequest request) {
        String token = request.getHeader("accessToken");
        Long userId = jwtService.getUserId(token);
        Long orderId = orderIdDto.getOrderId();

        User user = userRepo.findById(userId).<CustomApiException>orElseThrow(() -> {
            throw new CustomApiException("?????? ????????? ?????? ???????????????.");
        });
        Order order = orderRepo.findById(orderId).<CustomApiException>orElseThrow(() -> {
            throw new CustomApiException("?????? ????????? ?????? ???????????????.");
        });

        OrderDetailDto orderDetailDto = new OrderDetailDto(user, order);

        List<OrderItem> orderItemList = order.getOrderItems();
        for (OrderItem orderItem : orderItemList) {
            OrderItemDto orderItemDto = new OrderItemDto(orderItem);
            orderDetailDto.addOrderItemDto(orderItemDto);

            Review review = reviewRepo.findByUserIdAndOrderIdAndItemId(userId, orderId, orderItem.getItem().getId());
            if (review != null) {
                orderItemDto.addReviewInOrderDto(review);
                orderItemDto.setReviewEmpty(false); // ?????? ??????
            } else {
                orderItemDto.setReviewEmpty(true); // ?????? ??????
            }
        }

        return orderDetailDto;
    }

    /**
     * ?????? ??????
     * @param request
     * @return
     */
    @Transactional(readOnly = true)
    public Page<OrderHistRespDto> getOrderList(HttpServletRequest request, Pageable pageable) {
        String token = request.getHeader("accessToken");
        Long userId = jwtService.getUserId(token);

        List<Order> orderList = orderRepo.findByUserIdOrderByIdDesc(userId, pageable);
        Long totalCount = orderRepo.countByUserId(userId);
        List<OrderHistRespDto> orderHistRespDtoList = new ArrayList<>();

        for (Order order : orderList) {
            OrderHistRespDto orderHistRespDto = new OrderHistRespDto(order);
            List<OrderItem> orderItemList = order.getOrderItems();

            for (OrderItem orderItem : orderItemList) {
                Review review = reviewRepo.findByUserIdAndOrderIdAndItemId(userId, orderItem.getOrder().getId(), orderItem.getItem().getId());
                OrderItemDto orderItemDto = new OrderItemDto(orderItem);
                orderItemDto.addReviewInOrderDto(review);
                if (review != null) {
                    orderItemDto.setReviewEmpty(false);
                } else {
                    orderItemDto.setReviewEmpty(true);
                }
                orderHistRespDto.addOrderItemDto(orderItemDto);
            }

            orderHistRespDtoList.add(orderHistRespDto);
        }

        return new PageImpl<>(orderHistRespDtoList, pageable, totalCount);
    }

    /**
     * ???????????? ??????
     * @param passwordModifyDto
     */
    @Transactional
    public void passwordModify(PasswordModifyDto passwordModifyDto, HttpServletRequest request) {
        String token = request.getHeader("accessToken");
        Long userId = jwtService.getUserId(token);
        String rawPassword = passwordModifyDto.getPassword();

        User user = userRepo.findById(userId).<CustomApiException>orElseThrow(() -> {
            throw new CustomApiException("?????? ????????? ?????? ??? ????????????.");
        });

        if (bCryptPasswordEncoder.matches(rawPassword, user.getPassword())) {
            throw new CustomApiException("??????????????? ??????????????? ?????? ??????????????? ????????????.");
        }

        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
    }

    /**
     * ???????????? ?????? ??? ?????? ???????????? ??????
     * @return
     */
    @Transactional(readOnly = true)
    public boolean currentPasswordCheck(PasswordValidationDto passwordValidationDto, HttpServletRequest request) {
        String token = request.getHeader("accessToken");
        Long userId = jwtService.getUserId(token);
        String currentPassword = passwordValidationDto.getCurrentPassword();

        User user = userRepo.findById(userId).<CustomApiException>orElseThrow(() -> {
            throw new CustomApiException("?????? ????????? ?????? ??? ????????????.");
        });

        return bCryptPasswordEncoder.matches(currentPassword, user.getPassword()) ? true : false;
    }

    /**
     * ??????????????? ??????
     * @param request
     * @return
     */
    @Transactional(readOnly = true)
    public List<UserCouponDto> getCouponPoint(HttpServletRequest request) {
        String token = request.getHeader("accessToken");
        Long userId = jwtService.getUserId(token);

        List<UserCoupon> userCouponList = userCouponRepoSup.getUserCouponList(userId);
        List<UserCouponDto> userCouponDtoList = new ArrayList<>();

        // ????????? ???????????? ????????? ?????? ?????? userCouponDto??? ????????? userDataDto??? List??? ?????????
        if (userCouponList.size() != 0) {
            for (UserCoupon userCoupon : userCouponList) {
                UserCouponDto userCouponDto = new UserCouponDto(userCoupon);
                userCouponDtoList.add(userCouponDto);
            }
        }

        return userCouponDtoList;
    }


    /**
     * ??????????????? ?????? ?????? ????????????
     * @param request
     * @return
     */
    @Transactional(readOnly = true)
    public UserDataRespDto getUserData(HttpServletRequest request) {
        String accessToken = request.getHeader("accessToken");
        Long userId = jwtService.getUserId(accessToken);

        User user = userRepo.findById(userId).<CustomApiException>orElseThrow(() -> {
            throw new CustomApiException("?????? ????????? ?????? ???????????????.");
        });

        UserDataRespDto userDataRespDto = new UserDataRespDto(user);
        return userDataRespDto;
    }
}
