package com.ssafy.miro.board.application;

import com.ssafy.miro.board.application.response.BoardItems;
import com.ssafy.miro.board.domain.Board;
import com.ssafy.miro.board.domain.repository.BoardRepository;
import com.ssafy.miro.board.exception.BoardNotFoundException;
import com.ssafy.miro.board.presentation.request.BoardRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ssafy.miro.common.code.ErrorCode.NOT_FOUND_BOARD_ID;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public Long save(BoardRequest boardRequest) {
        Board newBoard = boardRepository.save(new Board(boardRequest.title(), boardRequest.content()));
        return newBoard.getId();
    }

    public List<BoardItems> getBoards() {
        return boardRepository.findAll().stream().map(BoardItems::of).toList();
    }

    public BoardItems getBoard(Long id) {
        return boardRepository.findById(id).map(BoardItems::of)
                .orElseThrow(()->new BoardNotFoundException(NOT_FOUND_BOARD_ID));
    }

    @Transactional
    public void updateBoard(Long id, BoardRequest boardRequest) {
        Board board = boardRepository.findById(id).orElseThrow(()->new BoardNotFoundException(NOT_FOUND_BOARD_ID));
        board.update(boardRequest.content(), board.getTitle());
    }

    @Transactional
    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }
}
