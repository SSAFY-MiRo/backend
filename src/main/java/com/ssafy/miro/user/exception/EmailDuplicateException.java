package com.ssafy.miro.user.exception;

import com.ssafy.miro.common.code.ErrorCode;
import lombok.Getter;

@Getter
public class EmailDuplicateException extends RuntimeException {
    private final ErrorCode errorCode;
    public EmailDuplicateException(final ErrorCode errorCode) {
        this.errorCode=errorCode;
    }
}
