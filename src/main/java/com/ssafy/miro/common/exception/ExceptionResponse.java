package com.ssafy.miro.common.exception;

import com.ssafy.miro.common.code.ErrorCode;
import org.springframework.http.HttpStatus;

public record ExceptionResponse(
        int code,
        HttpStatus status,
        String message
) {
    public ExceptionResponse(final ErrorCode errorCode) {
        this(errorCode.getCode(), errorCode.getHttpStatus(), errorCode.getMessage());
    }

    public ExceptionResponse(final ErrorCode errorCode, final String message){
        this(errorCode.getCode(), errorCode.getHttpStatus(), message);
    }
}
