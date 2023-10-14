package com.study.springbootwebboard.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.study.springbootwebboard.domain.Board;
import com.study.springbootwebboard.domain.QBoard;
import com.study.springbootwebboard.domain.QReply;
import com.study.springbootwebboard.dto.BoardListReplyCountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {
    // QuerydslRepositorySupport? Spring Data JPA와 QueryDSL을 함께 사용하여 JPA 엔터티를 쿼리하는 데 도움을 주는 유틸리티 클래스
    // 클래스에 EntityManager가 선언되어 있다.

    // 부모 클래스 QuerydslRepositorySupport에 Board 엔티티 클래스 전달
    public BoardSearchImpl() {
        super(Board.class);
    }

    @Override
    public Page<Board> search1(Pageable pageable) {
        // 단순히 제목 검색

        QBoard board = QBoard.board;            // Querydsl 도메인 객체 board 생성

        JPQLQuery<Board> query = from(board);   // select ... from board
        query.where(board.title.contains("1")); // where title like ...

        this.getQuerydsl().applyPagination(pageable, query);    // 페이징 처리. limit 적용

        List<Board> list = query.fetch();       // JPQLQuery 실행. 검색 결과를 리스트로 가져옴
        long count = query.fetchCount();        // count 쿼리 실행. 검색된 전체 게시물 수 가져옴

        return null;

    }

    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {
        // 다양한 검색 조건과 키워드를 이용한 검색

        QBoard board = QBoard.board;
        JPQLQuery<Board> query = from(board);   // select ... from board

        // 검색 조건과 키워드가 있다면
        if((types != null && types.length > 0) && keyword != null) {
            BooleanBuilder booleanBuilder = new BooleanBuilder();   // (

            for(String type : types) {
                // 검색 조건에 따라 이 중 하나를 OR 조건에 추가
                switch (type) {
                    case "t":
                        booleanBuilder.or(board.title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(board.content.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(board.writer.contains(keyword));
                        break;
                }
            }   // end for
            query.where(booleanBuilder);
        }   // end if
        // bno > 0 => 게시물 번호가 0보다 큰 경우만 검색하도록 조건 추가
        query.where(board.bno.gt(0L));
        // paging
        this.getQuerydsl().applyPagination(pageable, query);

        List<Board> list = query.fetch();
        long count = query.fetchCount();

        // 검색 결과를 Page<Board> 형태로 반환
        return new PageImpl<>(list, pageable, count);

    }

    @Override
    public Page<BoardListReplyCountDTO> searchWithReplyCount(String[] types, String keyword, Pageable pageable) {
        // 목록에 댓글 개수 보이게 검색하기

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;

        // board가 왼쪽, reply가 오른쪽 기준으로 left join 해서 게시물 정보를 모두 가져오고 해당 게시물에 댓글이 있으면 가져온다.
        JPQLQuery<Board> query = from(board)
                // reply 엔티티의 board 속성과 board 엔티티의 식별자(bno)를 비교해서 두 엔티티 간의 관계 설정
                .leftJoin(reply).on(reply.board.eq(board))
                // 결과 집합을 board 엔티티 기준으로 그룹화 => 각 게시물을 기준으로 결과를 그룹화
                .groupBy(board);

        if ((types != null && types.length > 0) && keyword != null) {
            BooleanBuilder booleanBuilder = new BooleanBuilder();   // (

            for (String type : types) {
                switch (type) {
                    case "t":
                        booleanBuilder.or(board.title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(board.content.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(board.writer.contains(keyword));
                        break;
                }
            }   // end for
            query.where(booleanBuilder);
        }   // end if
        // bno > 0 => 게시물 번호가 0보다 큰 경우만 검색하도록 조건 추가
        query.where(board.bno.gt(0L));

        // Projection? JPA에서 제공하는 JPQL의 결과를 바로 DTO로 처리하는 기능 => Querydsl도 이러한 기능을 제공한다.
        // 어떻게? Projections.bean()을 이용해 한번에 DTO로 처리할 수 있는데 이를 위해 JPQLQuery 객체의 select()를 이용한다.
        JPQLQuery<BoardListReplyCountDTO> dtoQuery = query.select(Projections.bean(BoardListReplyCountDTO.class,
                board.bno,
                board.title,
                board.writer,
                board.regDate,
                reply.count().as("replyCount")  // 댓글 수를 집계해서 replyCount라는 이름으로 BoardListReplyCountDTO에 매핑
        ));

        // 페이징
        this.getQuerydsl().applyPagination(pageable, dtoQuery);

        List<BoardListReplyCountDTO> dtoList = dtoQuery.fetch();
        long count = dtoQuery.fetchCount();

        // 검색 결과를 Page<BoardListReplyCountDTO> 형태로 반환
        return new PageImpl<>(dtoList, pageable, count);

    }
}
