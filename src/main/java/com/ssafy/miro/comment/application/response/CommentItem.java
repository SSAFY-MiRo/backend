package com.ssafy.miro.comment.application.response;

import com.ssafy.miro.comment.domain.Comment;

public record CommentItem(
        Long id,
        Long userId,
        String nickname,
        String content
) {
    public static CommentItem of(Comment comment) {
        return new CommentItem(comment.getId(), comment.getUser().getId(), comment.getUser().getNickname(), comment.getContent());
    }
}