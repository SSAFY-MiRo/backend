package com.ssafy.miro.user.exception;

import com.ssafy.miro.common.code.ErrorCode;
import lombok.Getter;

@Getter
public class NonValidationPasswordException extends RuntimeException {
    private final ErrorCode errorCode;

    public NonValidationPasswordException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
