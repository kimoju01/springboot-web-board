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

    @ApiOperation(value = "Replies of Board", notes = "GET 방식으로 특정 게시물의 댓글 목록 조회")
    @GetMapping(value = "/list/{bno}")
    public PageResponseDTO<ReplyDTO> getList(@PathVariable("bno") Long bno,
                                             PageRequestDTO pageRequestDTO) {
        // @PathVariable? 경로 변수를 메서드의 매개변수와 매핑.
        // 예를 들어 클라이언트에서 /list/123 같은 값이 들어오면 bno에 123이라는 값이 할당 됨

        PageResponseDTO<ReplyDTO> responseDTO = replyService.listOfBoard(bno, pageRequestDTO);

        return responseDTO;

    }

    @ApiOperation(value = "Read Reply", notes = "GET 방식으로 특정 댓글 조회")
    @GetMapping(value = "/{rno}")
    public ReplyDTO getReplyDTO(@PathVariable("rno") Long rno) {
        // 해당 댓글 데이터가 존재하지 않으면 서비스 계층 -> Optional<T> -> orElseThrow() -> 컨트롤러에 예외 전달 -> 예외 발생
        // 500 에러가 발생하기 때문에 CustomRestAdvice에서 예외 처리를 추가해주어야함
        ReplyDTO replyDTO = replyService.read(rno);

        return replyDTO;

    }

    @ApiOperation(value = "Delete Reply", notes = "DELETE 방식으로 특정 댓글 삭제")
    @DeleteMapping("/{rno}")
    public Map<String, Long> remove(@PathVariable("rno") Long rno) {
        // 존재하지 않는 번호의 댓글을 삭제하려고 하면 EmptyResultDataAccessException 예외 발생

        replyService.remove(rno);

        Map<String, Long> resultMap = new HashMap<>();

        resultMap.put("rno", rno);

        return resultMap;

    }

    @ApiOperation(value = "Modify Reply", notes = "PUT 방식으로 특정 댓글 수정")
    @PutMapping(value = "/{rno}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> modify(@PathVariable("rno") Long rno,
                                    @RequestBody ReplyDTO replyDTO) {

        replyDTO.setRno(rno);   // 번호 일치시킴

        replyService.modify(replyDTO);

        Map<String, Long> resultMap = new HashMap<>();

        resultMap.put("rno", rno);

        return resultMap;

    }

}
