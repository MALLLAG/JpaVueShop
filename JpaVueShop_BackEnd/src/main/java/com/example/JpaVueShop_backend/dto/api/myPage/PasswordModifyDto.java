package com.example.JpaVueShop_backend.dto.api.myPage;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter @Setter
public class PasswordModifyDto {

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z]).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;
}
