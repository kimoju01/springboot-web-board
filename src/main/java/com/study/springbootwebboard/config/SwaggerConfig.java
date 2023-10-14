package com.study.springbootwebboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    // Swagger UI 설정
    // http://localhost:8080/swagger-ui/index.html

    @Bean
    public Docket api() {
        // Swagger 문서의 기본 구성을 설정
        return new Docket(DocumentationType.OAS_30)
                .useDefaultResponseMessages(false)  // 기본 응답 메시지 사용 X
                .select()                                  // API 문서화 대상 선택
                .apis(RequestHandlerSelectors.basePackage("com.study.springbootwebboard.controller"))   // 패키지의 컨트롤러 클래스를 문서화 대상으로 지정
                .paths(PathSelectors.any())                // 모든 경로를 문서화 대상으로 지정
                .build()
                .apiInfo(apiInfo());                       // API 문서 정보 구성
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Springboot-web-board Projects Swagger")     // API 문서 제목 설정
                .build();
    }

}
