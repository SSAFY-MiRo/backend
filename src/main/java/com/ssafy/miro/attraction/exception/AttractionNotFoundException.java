package com.ssafy.miro.attraction.exception;

import com.ssafy.miro.common.code.ErrorCode;
import lombok.Getter;

@Getter
public class AttractionNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;

    public AttractionNotFoundException(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
