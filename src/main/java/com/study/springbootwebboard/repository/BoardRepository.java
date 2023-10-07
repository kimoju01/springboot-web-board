package com.study.springbootwebboard.repository;

import com.study.springbootwebboard.domain.Board;
import com.study.springbootwebboard.repository.search.BoardSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearch {
}
