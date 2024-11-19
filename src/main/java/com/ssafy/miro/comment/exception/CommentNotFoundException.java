package com.ssafy.miro.comment.exception;

import com.ssafy.miro.common.code.ErrorCode;
import lombok.Getter;

@Getter
public class CommentNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;

    public CommentNotFoundException(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
