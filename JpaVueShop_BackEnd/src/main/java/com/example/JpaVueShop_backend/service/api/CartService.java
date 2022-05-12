package com.example.JpaVueShop_backend.service.api;

import com.example.JpaVueShop_backend.config.jwt.JwtService;
import com.example.JpaVueShop_backend.domain.cart.Cart;
import com.example.JpaVueShop_backend.domain.cart.CartRepo;
import com.example.JpaVueShop_backend.domain.cartItem.CartItem;
import com.example.JpaVueShop_backend.domain.cartItem.CartItemRepo;
import com.example.JpaVueShop_backend.domain.cartItem.CartItemRepoSup;
import com.example.JpaVueShop_backend.domain.item.Item;
import com.example.JpaVueShop_backend.domain.item.ItemRepo;
import com.example.JpaVueShop_backend.domain.user.User;
import com.example.JpaVueShop_backend.domain.user.UserRepo;
import com.example.JpaVueShop_backend.domain.userCoupon.UserCoupon;
import com.example.JpaVueShop_backend.domain.userCoupon.UserCouponRepoSup;
import com.example.JpaVueShop_backend.dto.api.cart.CartOrderDto;
import com.example.JpaVueShop_backend.dto.api.cart.CartRespDto;
import com.example.JpaVueShop_backend.dto.api.cart.OrderUserDataDto;
import com.example.JpaVueShop_backend.dto.api.cartItem.CartItemIdDto;
import com.example.JpaVueShop_backend.dto.api.item.ItemIdDto;
import com.example.JpaVueShop_backend.dto.api.myPage.UserCouponDto;
import com.example.JpaVueShop_backend.dto.api.order.OrderDto;
import com.example.JpaVueShop_backend.handler.exeption.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final JwtService jwtService;
    private final OrderService orderService;
    private final UserRepo userRepo;
    private final ItemRepo itemRepo;
    private final CartRepo cartRepo;
    private final CartItemRepo cartItemRepo;
    private final CartItemRepoSup cartItemRepoSup;
    private final UserCouponRepoSup userCouponRepoSup;

    /**
     * 장바구니 상품들 주문
     * @param cartOrderDto
     * @param request
     */
    @Transactional
    public void cartOrder(CartOrderDto cartOrderDto, HttpServletRequest request) {
        String token = request.getHeader("accessToken");
        Long userId = jwtService.getUserId(token);
        Long userCouponId = cartOrderDto.getUserCouponId();
        int usedPoint = cartOrderDto.getUsedPoint();

        List<Long> cartItemIdList = cartOrderDto.getCartItemId();

        User user = userRepo.findById(userId).<CustomApiException>orElseThrow(() -> {
            throw new CustomApiException("해당 유저를 찾을 수 없습니다.");
        });

        List<OrderDto> orderDtoList = new ArrayList<>();

        // 장바구니에 담긴 상품들을 orderDto에 담아서 배열로 만든다
        for (Long cartItemId : cartItemIdList) {
            CartItem cartItem = cartItemRepo.findById(cartItemId).<CustomApiException>orElseThrow(() -> {
                throw new CustomApiException("장바구니에서 해당 상품을 찾을 수 없습니다.");
            });
            OrderDto orderDto = new OrderDto();
            orderDto.setItemId(cartItem.getItem().getId());
            orderDtoList.add(orderDto);
        }

        orderService.orders(orderDtoList, userCouponId, usedPoint, user);

        // 주문한 상품들 장바구니에서 삭제
        for (Long cartItemId : cartItemIdList) {
            CartItem cartItem = cartItemRepo.findById(cartItemId).<CustomApiException>orElseThrow(() -> {
                throw new CustomApiException("장바구니에서 해당 상품을 찾을 수 없습니다.");
            });
            cartItemRepo.delete(cartItem);
        }
    }

    /**
     * 장바구니 상품 삭제
     * @param cartItemIdDto
     */
    @Transactional
    public void deleteCartItem(CartItemIdDto cartItemIdDto) {
        Long cartItemId = cartItemIdDto.getCartItemId();

        CartItem cartItem = cartItemRepo.findById(cartItemId).<CustomApiException>orElseThrow(() -> {
            throw new CustomApiException("해당 상품을 찾지 못했습니다.");
        });

        cartItemRepo.delete(cartItem);
    }

    /**
     * 장바구니 상품 담기
     * @param itemIdDto
     */
    @Transactional
    public Long addCart(ItemIdDto itemIdDto, HttpServletRequest request) {
        String token = request.getHeader("accessToken");
        Long userId = jwtService.getUserId(token);
        Long itemId = itemIdDto.getItemId();

        User user = userRepo.findById(userId).<CustomApiException>orElseThrow(() -> {
            throw new CustomApiException("해당 유저를 찾을 수 없습니다.");
        });

        Cart cart = cartRepo.findByUserId(userId).orElseGet(() -> Cart.createCart(user));
        cartRepo.save(cart);

        Item item = itemRepo.findById(itemId).<CustomApiException>orElseThrow(() -> {
            throw new CustomApiException("해당 상품을 찾지 못했습니다.");
        });

        CartItem cartItem = cartItemRepo.findByCartIdAndItemId(cart.getId(), itemId);

        if (cartItem == null) {
            cartItem = CartItem.createCartItem(cart, item);
            cartItemRepo.save(cartItem);

            return cartItem.getId();
        } else {
            throw new CustomApiException("이미 장바구니에 담긴 상품입니다.");
        }

    }

    /**
     * 주문 시 필요한 유저 데이터
     * @param request
     * @return
     */
    @Transactional(readOnly = true)
    public OrderUserDataDto getOrderUserData(HttpServletRequest request) {
        String token = request.getHeader("accessToken");
        Long userId = jwtService.getUserId(token);

        User user = userRepo.findById(userId).<CustomApiException>orElseThrow(() -> {
            throw new CustomApiException("해당 유저를 찾을 수 없습니다.");
        });

        OrderUserDataDto orderUserDataDto = new OrderUserDataDto(user);

        List<UserCoupon> userCouponList = userCouponRepoSup.getUserCouponList(userId);

        // 유저가 발급받은 쿠폰이 있을 경우 userCouponDto를 만들어 userDataDto에 List로 넣는다
        if (userCouponList.size() != 0) {
            for (UserCoupon userCoupon : userCouponList) {
                UserCouponDto userCouponDto = new UserCouponDto(userCoupon);
                orderUserDataDto.addUserCouponDtoList(userCouponDto);
            }
        }

        return orderUserDataDto;
    }

    /**
     * 장바구니 조회
     * @return
     */
    @Transactional
    public List<CartRespDto> getCartList(HttpServletRequest request) {
        String token = request.getHeader("accessToken");
        Long userId = jwtService.getUserId(token);

        User user = userRepo.findById(userId).<CustomApiException>orElseThrow(() -> {
            throw new CustomApiException("해당 유저를 찾을 수 없습니다.");
        });

        Cart cart = cartRepo.findByUserId(userId).orElseGet(() -> Cart.createCart(user));
        cartRepo.save(cart);

        List<CartItem> cartItemList = cartItemRepoSup.getCartItemList(cart.getId());
        List<CartRespDto> cartRespDtoList = new ArrayList<>();

        for (CartItem cartItem : cartItemList) {
            CartRespDto cartDto = new CartRespDto(cartItem);
            cartRespDtoList.add(cartDto);
        }

        return cartRespDtoList;
    }
}
