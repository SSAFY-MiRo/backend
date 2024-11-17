package com.ssafy.miro.board.domain.repository;

import com.ssafy.miro.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
