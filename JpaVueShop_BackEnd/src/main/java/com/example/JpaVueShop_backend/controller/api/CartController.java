package com.example.JpaVueShop_backend.controller.api;

import com.example.JpaVueShop_backend.dto.CMRespDto;
import com.example.JpaVueShop_backend.dto.api.cart.CartOrderDto;
import com.example.JpaVueShop_backend.dto.api.cartItem.CartItemIdDto;
import com.example.JpaVueShop_backend.dto.api.item.ItemIdDto;
import com.example.JpaVueShop_backend.service.api.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    /**
     * 장바구니 상품들 주문
     * @param cartOrderDto
     * @param bindingResult
     * @param request
     * @return
     */
    @PostMapping("/cartOrder")
    public CMRespDto<?> cartOrder(@Valid @RequestBody CartOrderDto cartOrderDto,
                                  BindingResult bindingResult,
                                  HttpServletRequest request) {
        cartService.cartOrder(cartOrderDto, request);
        return new CMRespDto<>(1, "주문 완료", null);
    }

    /**
     * 장바구니 상품 삭제
     * @param cartItemIdDto
     * @return
     */
    @PostMapping("/deleteCartItem")
    public CMRespDto<?> deleteCartItem(@Valid @RequestBody CartItemIdDto cartItemIdDto,
                                       BindingResult bindingResult) {
        cartService.deleteCartItem(cartItemIdDto);
        return new CMRespDto<>(1, "장바구니 상품 삭제 완료", null);
    }

    /**
     * 장바구니 상품 담기
     * @param itemIdDto
     * @return
     */
    @PostMapping("/addCart")
    public CMRespDto<?> addCart(@RequestBody ItemIdDto itemIdDto, HttpServletRequest request) {
        cartService.addCart(itemIdDto, request);
        return new CMRespDto<>(1, "장바구니 담기 완료", null);
    }

    /**
     * 주문 시 필요한 유저 데이터
     * @param request
     * @return
     */
    @GetMapping("/getOrderUserData")
    public CMRespDto<?> getOrderUserData(HttpServletRequest request) {
        return new CMRespDto<>(1, "유저 정보 가져오기 완료", cartService.getOrderUserData(request));
    }

    /**
     * 장바구니 조회
     * @return
     */
    @GetMapping("/getCartList")
    public CMRespDto<?> getCartList(HttpServletRequest request) {
        return new CMRespDto<>(1, "장바구니 조회 완료", cartService.getCartList(request));
    }
}
