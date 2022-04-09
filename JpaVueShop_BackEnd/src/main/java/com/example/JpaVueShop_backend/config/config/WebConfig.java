package com.example.JpaVueShop_backend.config.config;

import com.example.JpaVueShop_backend.config.AdminInterceptor;
import com.example.JpaVueShop_backend.config.jwt.JwtInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

//    private final JwtInterceptor jwtInterceptor;
//    private final AdminInterceptor adminInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

/*        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/api/cart/**")
                .addPathPatterns("/api/myPage/**")
                .excludePathPatterns("/api/user/**");

        // 관리자 권한 체크
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/admin/user/**")
                .addPathPatterns("/admin/editor/**")
                .addPathPatterns("/admin/order/**")
                .addPathPatterns("/admin/coupon/**");*/

    }
}
