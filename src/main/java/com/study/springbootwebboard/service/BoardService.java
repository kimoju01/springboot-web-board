package com.study.springbootwebboard.service;

import com.study.springbootwebboard.dto.BoardDTO;
import com.study.springbootwebboard.dto.PageRequestDTO;
import com.study.springbootwebboard.dto.PageResponseDTO;

public interface BoardService {

    Long register(BoardDTO boardDTO);
    BoardDTO readOne(Long bno);
    void modify(BoardDTO boardDTO);
    void remove(Long bno);
    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);

}
