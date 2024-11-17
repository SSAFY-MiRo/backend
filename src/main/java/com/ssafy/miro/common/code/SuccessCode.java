package com.ssafy.miro.common.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {
    OK(2000, HttpStatus.OK, "성공했습니다.");

    private final int code;
    private final HttpStatus httpStatus;
    private final String message;
}
