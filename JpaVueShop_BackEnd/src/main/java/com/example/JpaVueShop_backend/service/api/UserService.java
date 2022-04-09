package com.example.JpaVueShop_backend.service.api;

import com.example.JpaVueShop_backend.domain.user.User;
import com.example.JpaVueShop_backend.domain.user.UserRepo;
import com.example.JpaVueShop_backend.dto.api.JoinReqDto;
import com.example.JpaVueShop_backend.handler.exeption.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    /**
     * 회원가입
     * @param joinReqDto
     * @return
     */
    @Transactional
    public Long join(JoinReqDto joinReqDto) {
        if (userRepo.existsByUsername(joinReqDto.getUsername())) {
            throw new CustomApiException("해당 아이디는 이미 사용중입니다.");
        }

        User user = User.createUser(joinReqDto);
        userRepo.save(user);

        return user.getId();
    }
}
