package com.study.springbootwebboard.service;

import com.study.springbootwebboard.dto.BoardDTO;

public interface BoardService {

    Long register(BoardDTO boardDTO);
    BoardDTO readOne(Long bno);
    void modify(BoardDTO boardDTO);
    void remove(Long bno);

}
