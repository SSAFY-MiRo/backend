package com.ssafy.miro.common.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {
    OK(1000, HttpStatus.OK, "성공했습니다."),

    //Board
    CREATE_BOARD(2000, HttpStatus.CREATED, "게시글 등록 성공했습니다."),
    UPDATE_BOARD(2001, HttpStatus.OK, "게시글 업데이트 성공했습니다."),
    DELETE_BOARD(2002,HttpStatus.OK,"게시물 삭제에 성공했습니다."),

    // Comment
    CREATED_COMMENT(2000, HttpStatus.CREATED, "댓글 등록 성공했습니다."),
    UPDATE_COMMENT(2001, HttpStatus.CREATED, "댓글 수정 성공했습니다."),
    DELETE_COMMENT(2002, HttpStatus.NO_CONTENT, "댓글 삭제 성공했습니다."),
    ;

    private final int code;
    private final HttpStatus httpStatus;
    private final String message;
}
