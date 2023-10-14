package com.study.springbootwebboard.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardListReplyCountDTO {
    // 게시물 목록 화면에 해당 게시물 댓글 숫자 출력하기 위한 DTO

    private Long bno;
    private String title;
    private String writer;
    private LocalDateTime regDate;
    private Long replyCount;


}
