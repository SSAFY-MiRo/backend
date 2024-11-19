package com.ssafy.miro.comment.application.response;

import com.ssafy.miro.comment.domain.Comment;

public record CommentItems(
        Long id,
        Long userId,
        String nickname,
        String content
) {
    public static CommentItems of(Comment comment) {
        return new CommentItems(comment.getId(), comment.getUser().getId(), comment.getUser().getNickname(), comment.getContent());
    }
}