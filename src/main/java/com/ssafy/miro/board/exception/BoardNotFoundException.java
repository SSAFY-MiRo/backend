package com.ssafy.miro.board.exception;

import com.ssafy.miro.common.code.ErrorCode;
import lombok.Getter;

@Getter
public class BoardNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;

    public BoardNotFoundException(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
