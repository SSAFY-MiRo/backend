package com.ssafy.miro.comment.presentation.request;

import com.ssafy.miro.comment.domain.Comment;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentAddRequest {
    @NotBlank
    private String content;
    @NotBlank
    private Long articleId;
}
