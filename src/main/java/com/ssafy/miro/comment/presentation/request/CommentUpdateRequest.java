package com.ssafy.miro.comment.presentation.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentUpdateRequest {
    @NotBlank
    private Long id;
    @NotBlank
    private String content;
}
