package com.ssafy.miro.common.exception;

import com.ssafy.miro.common.code.ErrorCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class GlobalException extends RuntimeException {
    private final ErrorCode errorCode;
    public GlobalException(final ErrorCode errorCode) {
        log.error("ErrorCode = {}", errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
