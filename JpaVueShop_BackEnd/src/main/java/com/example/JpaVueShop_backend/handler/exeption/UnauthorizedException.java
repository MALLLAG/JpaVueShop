package com.example.JpaVueShop_backend.handler.exeption;

public class UnauthorizedException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public int code;

    public UnauthorizedException() {
    }

}
