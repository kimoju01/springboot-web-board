package com.study.springbootwebboard.service;

import com.study.springbootwebboard.dto.PageRequestDTO;
import com.study.springbootwebboard.dto.PageResponseDTO;
import com.study.springbootwebboard.dto.ReplyDTO;

public interface ReplyService {

    // 댓글 등록
    Long register(ReplyDTO replyDTO);
    ReplyDTO read(Long rno);
    void modify(ReplyDTO replyDTO);
    void remove(Long rno);

    // 게시물에 있는 댓글 목록 페이징
    PageResponseDTO<ReplyDTO> listOfBoard(Long bno, PageRequestDTO pageRequestDTO);

}
