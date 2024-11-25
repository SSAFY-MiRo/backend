package com.ssafy.miro.comment.application.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.miro.comment.domain.Comment;

import java.time.LocalDateTime;

public record CommentItem(
        Long id,
        Long userId,
        String nickname,
        String content,
        @JsonFormat(pattern = "yyyy.MM.dd")
        LocalDateTime createdTime
) {
    public static CommentItem of(Comment comment) {
        return new CommentItem(comment.getId(), comment.getUser().getId(), comment.getUser().getNickname(), comment.getContent(), comment.getCreatedAt());
    }
}