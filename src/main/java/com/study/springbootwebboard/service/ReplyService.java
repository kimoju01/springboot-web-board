package com.study.springbootwebboard.service;

import com.study.springbootwebboard.dto.ReplyDTO;

public interface ReplyService {

    // 댓글 등록
    Long register(ReplyDTO replyDTO);

}
