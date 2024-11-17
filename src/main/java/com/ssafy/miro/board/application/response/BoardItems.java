package com.ssafy.miro.board.application.response;

import com.ssafy.miro.board.domain.Board;

public record BoardItems(
        Long id,
        String title,
        String content,
        Long view
) {
    public static BoardItems of(Board board) {
        return new BoardItems(board.getId(), board.getTitle(), board.getContent(), board.getView());
    }
}
