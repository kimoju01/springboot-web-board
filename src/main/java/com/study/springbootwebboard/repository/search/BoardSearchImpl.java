package com.study.springbootwebboard.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.study.springbootwebboard.domain.Board;
import com.study.springbootwebboard.domain.QBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {

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

}
