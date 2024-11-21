package com.ssafy.miro.common.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INVALID_REQUEST(1000, BAD_REQUEST, "올바르지 않은 요청입니다."),
    INVALID_IMAGE_FORMAT(1001, BAD_REQUEST, "지원하지 않는 이미지 형식입니다."),
    EXCEED_IMAGE_CAPACITY(1002, BAD_REQUEST, "이미지 크기가 허용된 최대 용량을 초과했습니다."),
    FAIL_IMAGE_UPLOAD(1003, BAD_REQUEST, "이미지 업로드에 실패했습니다."),

    INVALID_AUTHORITY(2001, FORBIDDEN, "해당 리소스에 접근할 권한이 없습니다."),
    INVALID_PASSWORD(2002, UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    INVALID_ACCESS_TOKEN(2003, UNAUTHORIZED, "유효하지 않은 AccessToken 입니다."),
    INVALID_REFRESH_TOKEN(2004, UNAUTHORIZED, "유효하지 않은 RefreshToken 입니다."),
    INVALID_AUTHORIZATION_CODE(2005, UNAUTHORIZED, "유효하지 않은 인가 코드 입니다."),
    EXPIRED_ACCESS_TOKEN(2006, UNAUTHORIZED, "만료된 AccessToken 입니다."),
    EXPIRED_REFRESH_TOKEN(2007, UNAUTHORIZED, "만료된 RefreshToken 입니다."),
    FAIL_OAUTH_USERINFO_RETRIEVAL(2008, UNAUTHORIZED, "회원 정보를 가져오는데 실패했습니다."),

    //User
    NOT_FOUND_BOARD_ID(3001, NOT_FOUND, "요청한 ID에 해당하는 게시물이 존재하지 않습니다."),
    NOT_FOUND_USER_ID(3002, NOT_FOUND, "요청한 ID에 해당하는 사용자가 존재하지 않습니다."),
    NOT_FOUND_USER_EMAIL(3003, NOT_FOUND, "요청한 이메일에 해당하는 사용자가 존재하지 않습니다."),
    NOT_FOUND_IMAGE_FILE(3004, NOT_FOUND, "요청한 이미지 파일을 찾을 수 없습니다."),
    NOT_FOUND_COMMENT_ID(3005, NOT_FOUND, "요청한 ID에 해당하는 댓글이 존재하지 않습니다."),
    EMAIL_DUPLICATED(3005, CONFLICT, "이메일이 이미 존재합니다."),
    NON_VALIDATED_PASSWORD(3006, UNAUTHORIZED, "비밀번호가 유효하지 않습니다."),

    //Attraction
    NOT_FOUND_ATTRACTION_ID(3101, NOT_FOUND, "요청한 ID에 해당하는 관광지가 존재하지 않습니다."),

    //Plan
    NOT_FOUND_PLAN_ID(3201, NOT_FOUND, "요청한 ID에 해당하는 여행 계획이 존재하지 않습니다."),

    //Email
    NOT_FOUND_EMAIL_TOKEN_ID(3201, NOT_FOUND, "이메일 검증에 실패했습니다."),

    SERVER_ERROR(4000, INTERNAL_SERVER_ERROR, "서버 에러가 발생하였습니다.");

    private final int code;
    private final HttpStatus httpStatus;
    private final String message;
}
