package com.example.JpaVueShop_backend.handler.exeption;

public class RefreshTokenExpired extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public int code;

    public RefreshTokenExpired() {
    }

}
