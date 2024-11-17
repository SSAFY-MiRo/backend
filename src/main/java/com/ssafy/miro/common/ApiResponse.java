package com.ssafy.miro.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.ssafy.miro.common.code.ErrorCode;
import com.ssafy.miro.common.code.SuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public record ApiResponse<T>(@JsonProperty("isSuccess") boolean isSuccess,
                             int code,
                             String message,
                             @JsonInclude(JsonInclude.Include.NON_NULL) T result) {
    // 성공한 경우
    public static <T> ApiResponse<T> onSuccess(T result) {
        return new ApiResponse<>(true, SuccessCode.OK.getCode(), SuccessCode.OK.getMessage(), result);
    }

    public static <T> ApiResponse<T> of(SuccessCode successCode, T result) {
        return new ApiResponse<>(true, successCode.getCode(), successCode.getMessage(), result);
    }

    // 실패한 경우
    public static <T> ApiResponse<T> onFailure(ErrorCode errorCode, T result) {
        return new ApiResponse<>(false, errorCode.getCode(), errorCode.getMessage(), result);
    }

}
