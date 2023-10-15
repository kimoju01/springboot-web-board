package com.study.springbootwebboard.service;

import com.study.springbootwebboard.dto.BoardDTO;
import com.study.springbootwebboard.dto.BoardListReplyCountDTO;
import com.study.springbootwebboard.dto.PageRequestDTO;
import com.study.springbootwebboard.dto.PageResponseDTO;

public interface BoardService {

    Long register(BoardDTO boardDTO);
    BoardDTO readOne(Long bno);
    void modify(BoardDTO boardDTO);
    void remove(Long bno);
    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);

    // 게시물 목록과 댓글 수 같이 가져오기
    PageResponseDTO<BoardListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO);

}
