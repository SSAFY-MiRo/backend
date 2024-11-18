package com.ssafy.miro.article.exception;

import com.ssafy.miro.common.code.ErrorCode;
import lombok.Getter;

@Getter
public class ArticleNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;

    public ArticleNotFoundException(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
