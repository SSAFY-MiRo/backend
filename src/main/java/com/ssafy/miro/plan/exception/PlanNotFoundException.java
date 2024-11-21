package com.ssafy.miro.plan.exception;

import com.ssafy.miro.common.code.ErrorCode;
import lombok.Getter;

@Getter
public class PlanNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;

    public PlanNotFoundException(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
