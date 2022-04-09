package com.example.JpaVueShop_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class CMRespDto<T> {
    private int code; // 1 성공, -1 실패
    private String message;
    private T data;
}
