package com.ssafy.miro.common.exception;

import com.ssafy.miro.common.code.ErrorCode;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {
    private final ErrorCode errorCode;
    public GlobalException(final ErrorCode errorCode) {
        System.out.println(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
