package com.ssafy.miro.common.exception;

import com.ssafy.miro.article.exception.ArticleNotFoundException;
import com.ssafy.miro.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.ssafy.miro.common.code.ErrorCode.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException e,
            final HttpHeaders headers,
            final HttpStatusCode status,
            final WebRequest request
    ) {
        log.warn(e.getMessage(), e);
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.onFailure(INVALID_REQUEST, null));
    }

    @ExceptionHandler(ArticleNotFoundException.class)
    public ResponseEntity<Object> handleBoardNotFoundException(final ArticleNotFoundException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(ApiResponse.onFailure(e.getErrorCode(),null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(final Exception e){
        log.error(e.getMessage(), e);
        return ResponseEntity.internalServerError()
                .body(ApiResponse.onFailure(SERVER_ERROR, null));
    }
}
