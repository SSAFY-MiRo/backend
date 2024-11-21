package com.ssafy.miro.user.exception;

import com.ssafy.miro.common.code.ErrorCode;
import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;
    public UserNotFoundException(final ErrorCode errorCode) {
        this.errorCode=errorCode;
    }
}
