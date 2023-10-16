package com.study.springbootwebboard.controller;

import com.study.springbootwebboard.dto.PageRequestDTO;
import com.study.springbootwebboard.dto.PageResponseDTO;
import com.study.springbootwebboard.dto.ReplyDTO;
import com.study.springbootwebboard.service.ReplyService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController // 메서드의 모든 리턴 값이 Thymeleaf로 전송되는게 아니라 바로 JSON으로 처리
@RequestMapping("/replies")
@Log4j2
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    // @ApiOperation? Swagger UI에서 해당 기능의 설명으로 출력
    // consumes 속성? 해당 메서드를 받아서 소비하는 데이터가 어떤 종류인지 명시
    // => JSON 타입의 데이터를 처리하는 메서드임을 명시하고 있다.
    @ApiOperation(value = "Replies POST", notes = "POST 방식으로 댓글 등록")
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> register(@Valid @RequestBody ReplyDTO replyDTO,
                                                      BindingResult bindingResult) throws BindException {
        // @RequestBody? JSON 문자열을 ReplyDTO로 변환. ReplyDTO를 이용해 파라미터 수집
        log.info(replyDTO);

        // 문제가 있을 때는 BindException을 CustomRestAdvice.handleBindException() 메서드로 던진다.
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        Map<String, Long> resultMap = new HashMap<>();

        Long rno = replyService.register(replyDTO);
        resultMap.put("rno", rno);

        // 메서드 리턴값에 문제가 있다면 @RestControllerAdvice가 처리할 것이기 때문에 정상적인 결과만 리턴
        return resultMap;

    }

}
