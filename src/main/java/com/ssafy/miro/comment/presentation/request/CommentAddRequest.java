package com.ssafy.miro.comment.presentation.request;

import com.ssafy.miro.article.domain.Article;
import com.ssafy.miro.comment.domain.Comment;
import com.ssafy.miro.user.domain.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentAddRequest {
    @NotBlank
    private String content;
    @NotBlank
    private Long articleId;

    public Comment toComment(User user, Article article, String content) {
        return new Comment(null, user, article, content);
    }
}
