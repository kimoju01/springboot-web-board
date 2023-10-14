package com.study.springbootwebboard.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc   // Swagger UI 3.0 버전 사용 위해서 Spring Web 관련 설정 추가 (없으면 에러 발생)
public class CustomServletConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Swagger UI 적용되면 정적 파일 경로가 바뀌기 때문에 경로를 재정의해서 화면 제대로 출력되게 함
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/fonts/**")
                .addResourceLocations("classpath:/static/fonts");
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/assets/**")
                .addResourceLocations("classpath:/static/assets/");

    }
}
