package com.example.JpaVueShop_backend.service.admin;

import com.example.JpaVueShop_backend.domain.user.AdminUserRepo;
import com.example.JpaVueShop_backend.domain.user.AdminUserRepoSup;
import com.example.JpaVueShop_backend.domain.user.User;
import com.example.JpaVueShop_backend.dto.admin.user.UserRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final AdminUserRepo adminUserRepo;
    private final AdminUserRepoSup adminUserRepoSup;

    /**
     * 가입한 유저 리스트
     * @return
     */
    @Transactional(readOnly = true)
    public Page<UserRespDto> getUserList(Pageable pageable) {
        List<User> userList = adminUserRepo.findAllByOrderByIdDesc(pageable);
        List<UserRespDto> userRespDtoList = new ArrayList<>();
        Long totalCount = adminUserRepoSup.userCount();

        for (User user : userList) {
            UserRespDto userRespDto = new UserRespDto(user);
            userRespDtoList.add(userRespDto);
        }

        return new PageImpl<>(userRespDtoList, pageable, totalCount);
    }
}
