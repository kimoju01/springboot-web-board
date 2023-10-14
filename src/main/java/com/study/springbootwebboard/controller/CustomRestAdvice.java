package com.study.springbootwebboard.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice   // 컨트롤러에서 발생하는 예외에 대해 JSON과 같은 순수한 응답 메시지 생성해서 보낼 수 있음
@Log4j2
public class CustomRestAdvice {
    // RestController는 Ajax를 이용해 눈에 보이지 않는 방식으로 서버를 호출하고 결과를 전송
    // 즉, 에러가 발생하면 알아 보기 힘들다. => @Valid 과정에서 문제 발생하면 처리하기 위한 Controller

    @ExceptionHandler(BindException.class)          // BindException 예외를 처리하는 핸들러 메서드임을 명시
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)  // 핸들러 메서드가 처리하는 예외에 대한 HTTP 응답 상태 코드 설정
    public ResponseEntity<Map<String, String>> handleBindException(BindException e) {
        // 컨트롤러에서 BindException이 던져지는 경우 이를 이용해서 JSON 메시지(Map<String, String>)와 400에러(Bad Request) 전송
        log.error(e);

        Map<String, String> errorMap = new HashMap<>();

        if (e.hasErrors()) {
            // BindException에서 발생한 바인딩 결과 가져옴
            BindingResult bindingResult = e.getBindingResult();
            // 바인딩 결과에서 필드 에러 목록 반복하며 각 필드 에러 처리
            bindingResult.getFieldErrors().forEach(FieldError -> {
                // 필드 에러 이름과 코드 errorMap에 추가
                errorMap.put(FieldError.getField(), FieldError.getCode());
            });
        }

        // 처리된 errorMap을 클라이언트에게 Bad Request(400) 상태 코드와 함께 에러 응답 반환
        return ResponseEntity.badRequest().body(errorMap);

    }
}