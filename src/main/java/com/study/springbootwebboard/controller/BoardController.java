package com.study.springbootwebboard.controller;

import com.study.springbootwebboard.dto.BoardDTO;
import com.study.springbootwebboard.dto.PageRequestDTO;
import com.study.springbootwebboard.dto.PageResponseDTO;
import com.study.springbootwebboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@Log4j2
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {

        PageResponseDTO<BoardDTO> pageResponseDTO = boardService.list(pageRequestDTO);
        log.info(pageResponseDTO);

        model.addAttribute("responseDTO", pageResponseDTO);

    }

    @GetMapping("/register")
    public void registerGET() {

    }

    @PostMapping("/register")
    public String registerPOST(@Valid BoardDTO boardDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        // @Valid, BindingResult를 통해 DTO 검증
        // RedirectAttributes: 리다이렉션을 통해 다른 페이지로 이동할 때 데이터를 함께 전달할 수 있다.

        log.info("board POST register..........");

        if(bindingResult.hasErrors()) {
            // 검증에 문제가 있다면 다시 입력 화면으로 리다이렉션
            // 처리 과정에서 잘못된 결과는 errors라는 이름의 일회용 데이터로 전송된다. (새로고침하면 삭제되는 데이터)
            log.info("has error..........");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            return "redirect:/board/register";
        }

        log.info(boardDTO);

        Long bno = boardService.register(boardDTO);
        // 정상적으로 등록 처리가 되면 목록 화면으로 이동했을 때 result라는 이름으로 게시물 번호(bno)를 담은 일회용 데이터가 전송된다.
        redirectAttributes.addFlashAttribute("result", bno);
        return "redirect:/board/list";

    }

}
