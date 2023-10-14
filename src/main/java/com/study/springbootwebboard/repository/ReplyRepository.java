package com.study.springbootwebboard.repository;

import com.study.springbootwebboard.domain.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    // JPQL 쿼리로 Reply 엔티티(r)에서 Board 엔티티를 통해 bno 컬럼을 기준으로 특정 게시물에 속하는 댓글 검색
    // :bno? 쿼리 매개변수로 메서드의 Long bno 매개변수와 연결
    @Query("select r from Reply r where r.board.bno = :bno")
    Page<Reply> listOfBoard(Long bno, Pageable pageable);



}
