package com.study.springbootwebboard.service;

import com.study.springbootwebboard.dto.BoardDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister() {
        log.info(boardService.getClass().getName());

        BoardDTO boardDTO = BoardDTO.builder()
                .title("title...")
                .content("content...")
                .writer("writer...")
                .build();

        Long bno = boardService.register(boardDTO);
        log.info(bno);
    }

    @Test
    public void testReadOne() {
        Long bno = 100L;

        BoardDTO boardDTO = boardService.readOne(bno);
        log.info(boardDTO);
    }

    @Test
    public void testModify() {
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(101L)
                .title("update title 101...")
                .content("update content 101...")
                .build();

        boardService.modify(boardDTO);
        log.info(boardDTO);
    }

    @Test
    public void testRemove() {
        Long bno = 10L;
        boardService.remove(bno);
    }

}
