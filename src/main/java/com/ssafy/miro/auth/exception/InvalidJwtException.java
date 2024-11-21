package com.ssafy.miro.auth.exception;

import com.ssafy.miro.common.code.ErrorCode;
import lombok.Getter;

@Getter
public class InvalidJwtException extends RuntimeException {
    private final ErrorCode errorCode;

    public InvalidJwtException(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
