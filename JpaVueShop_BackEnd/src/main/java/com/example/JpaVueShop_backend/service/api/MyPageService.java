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
    private final OrderRepoSup orderRepoSup;
    private final ReviewRepo reviewRepo;
    private final CouponRepo couponRepo;

    /**
     * 쿠폰 등록
     * @param registerCouponDto
     * @param request
     */
    @Transactional
    public void registerCoupon(RegisterCouponDto registerCouponDto, HttpServletRequest request) {
        String couponNumber = registerCouponDto.getCouponNumber();
        String token = request.getHeader("accessToken");
        Long userId = jwtService.getUserId(token);

        User user = userRepo.findById(userId).<CustomApiException>orElseThrow(() -> {
            throw new CustomApiException("해당 유저를 찾지 못했습니다.");
        });

        Coupon coupon = couponRepo.findByCouponNumber(couponNumber).<CustomApiException>orElseThrow(() -> {
            throw new CustomApiException("존재하지 않는 쿠폰입니다.");
        });

        // 쿠폰 유효기간을 문자열로 변환
        String startDateToString = String.valueOf(coupon.getStartDate()).substring(0, 10);
        String endDateToString = String.valueOf(coupon.getEndDate()).substring(0, 10);

        // 문자열로 변환한 쿠폰 유효기간과 오늘의 날짜를 비교할 수 있게 형식을 맞춤
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.KOREA);
        LocalDate startDate = LocalDate.parse(startDateToString, formatter);
        LocalDate endDate = LocalDate.parse(endDateToString, formatter);
        LocalDate now = LocalDate.now();

        // startDate 이전에 등록할 시
        if (now.compareTo(startDate) < 0) {
            throw new CustomApiException("존재하지 않는 쿠폰입니다.");
        }
        // endDate 이후에 등록할 시
        if (now.compareTo(endDate) > 0) {
            throw new CustomApiException("유효기간이 지난 쿠폰입니다.");
        }

        // 이미 지급받은 쿠폰인지 체크
        UserCoupon userCouponDuplicateCheck = userCouponRepo.findByCouponId(coupon.getId());
        if (userCouponDuplicateCheck != null) {
            throw new CustomApiException("이미 지급받은 쿠폰입니다.");
        }

        UserCoupon userCoupon = UserCoupon.createUserCoupon(user, coupon);
        userCouponRepo.save(userCoupon);
    }

    /**
     * 상세 주문 페이지 입장 전 주문한 유저가 맞는지 체크
     * @param orderIdDto
     * @param request
     */
    @Transactional(readOnly = true)
    public void checkUser(OrderIdDto orderIdDto, HttpServletRequest request) {
        String token = request.getHeader("accessToken");
        Long userId = jwtService.getUserId(token);
        Long orderId = orderIdDto.getOrderId();

        orderRepo.findByIdAndUserId(orderId, userId).<CustomApiException>orElseThrow(() -> {
            throw new CustomApiException("접근할 수 있는 권한이 없습니다.");
        });
    }

    /**
     * 상세 주문
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
            throw new CustomApiException("해당 유저를 찾지 못했습니다.");
        });
        Order order = orderRepo.findById(orderId).<CustomApiException>orElseThrow(() -> {
            throw new CustomApiException("해당 주문을 찾지 못했습니다.");
        });

        OrderDetailDto orderDetailDto = new OrderDetailDto(user, order);

        // 주문한 상품들을 orderItemDto에 담는다
        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            OrderItemDto orderItemDto = new OrderItemDto(orderItem);
            orderDetailDto.addOrderItemDto(orderItemDto);

            Review review = reviewRepo.findByUserIdAndOrderIdAndItemId(userId, orderId, orderItem.getItem().getId());
            if (review != null) {
                orderItemDto.addReviewInOrderDto(review);
                orderItemDto.setReviewEmpty(false); // 리뷰 있음
            } else {
                orderItemDto.setReviewEmpty(true); // 리뷰 없음
            }
        }

        return orderDetailDto;
    }

    /**
     * 주문 내역
     * @param request
     * @return
     */
    @Transactional(readOnly = true)
    public Page<OrderHistRespDto> getOrderList(HttpServletRequest request, Pageable pageable) {
        String token = request.getHeader("accessToken");
        Long userId = jwtService.getUserId(token);

        List<Order> orderList = orderRepo.findByUserIdOrderByIdDesc(userId, pageable);
        Long totalCount = orderRepoSup.orderCount(userId);
        List<OrderHistRespDto> orderHistRespDtoList = new ArrayList<>();

        // 주문 내역 DTO 생성
        for (Order order : orderList) {
            OrderHistRespDto orderHistRespDto = new OrderHistRespDto(order);
            List<OrderItem> orderItemList = order.getOrderItems();

            for (OrderItem orderItem : orderItemList) {
                Review review = reviewRepo.findByUserIdAndOrderIdAndItemId(userId, orderItem.getOrder().getId(), orderItem.getItem().getId());
                OrderItemDto orderItemDto = new OrderItemDto(orderItem);
                orderItemDto.addReviewInOrderDto(review);
                if (review != null) {
                    orderItemDto.setReviewEmpty(false); // 리뷰 있음
                } else {
                    orderItemDto.setReviewEmpty(true); // 리뷰 없음
                }
                orderHistRespDto.addOrderItemDto(orderItemDto);
            }

            orderHistRespDtoList.add(orderHistRespDto);
        }

        return new PageImpl<>(orderHistRespDtoList, pageable, totalCount);
    }

    /**
     * 비밀번호 변경
     * @param passwordModifyDto
     */
    @Transactional
    public void passwordModify(PasswordModifyDto passwordModifyDto, HttpServletRequest request) {
        String token = request.getHeader("accessToken");
        Long userId = jwtService.getUserId(token);
        String rawPassword = passwordModifyDto.getPassword();

        User user = userRepo.findById(userId).<CustomApiException>orElseThrow(() -> {
            throw new CustomApiException("해당 유저를 찾을 수 없습니다.");
        });

        if (bCryptPasswordEncoder.matches(rawPassword, user.getPassword())) {
            throw new CustomApiException("변경하려는 비밀번호가 현재 비밀번호와 같습니다.");
        }

        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
    }

    /**
     * 비밀번호 변경 시 현재 비밀번호 확인
     * @return
     */
    @Transactional(readOnly = true)
    public boolean currentPasswordCheck(PasswordValidationDto passwordValidationDto, HttpServletRequest request) {
        String token = request.getHeader("accessToken");
        Long userId = jwtService.getUserId(token);
        String currentPassword = passwordValidationDto.getCurrentPassword();

        User user = userRepo.findById(userId).<CustomApiException>orElseThrow(() -> {
            throw new CustomApiException("해당 유저를 찾을 수 없습니다.");
        });

        return bCryptPasswordEncoder.matches(currentPassword, user.getPassword()) ? true : false;
    }

    /**
     * 마이페이지 쿠폰/포인트
     * @param request
     * @return
     */
    @Transactional(readOnly = true)
    public List<UserCouponDto> getCouponPoint(HttpServletRequest request) {
        String token = request.getHeader("accessToken");
        Long userId = jwtService.getUserId(token);

        List<UserCoupon> userCouponList = userCouponRepoSup.getUserCouponList(userId);
        List<UserCouponDto> userCouponDtoList = new ArrayList<>();

        // 유저가 발급받은 쿠폰이 있을 경우 userCouponDto를 만들어 userDataDto에 List로 넣는다
        if (userCouponList.size() != 0) {
            for (UserCoupon userCoupon : userCouponList) {
                UserCouponDto userCouponDto = new UserCouponDto(userCoupon);
                userCouponDtoList.add(userCouponDto);
            }
        }

        return userCouponDtoList;
    }


    /**
     * 마이페이지 유저 정보 가져오기
     * @param request
     * @return
     */
    @Transactional(readOnly = true)
    public UserDataRespDto getUserData(HttpServletRequest request) {
        String accessToken = request.getHeader("accessToken");
        Long userId = jwtService.getUserId(accessToken);

        User user = userRepo.findById(userId).<CustomApiException>orElseThrow(() -> {
            throw new CustomApiException("해당 유저를 찾지 못했습니다.");
        });

        UserDataRespDto userDataRespDto = new UserDataRespDto(user);
        return userDataRespDto;
    }
}
