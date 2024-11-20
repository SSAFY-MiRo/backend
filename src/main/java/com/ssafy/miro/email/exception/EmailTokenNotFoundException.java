package com.ssafy.miro.email.exception;

import com.ssafy.miro.common.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class EmailTokenNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;
    public EmailTokenNotFoundException(final ErrorCode errorCode) {
        this.errorCode=errorCode;
    }
}
