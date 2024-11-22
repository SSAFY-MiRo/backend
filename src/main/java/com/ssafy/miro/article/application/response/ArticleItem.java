package com.ssafy.miro.article.application.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.miro.article.domain.Article;
import com.ssafy.miro.user.domain.User;

import java.time.LocalDateTime;

public record ArticleItem(
        Long id,
        String nickname,
        String title,
        String content,
        Long view,
        @JsonFormat(pattern = "yyyy.mm.dd")
        LocalDateTime createTime,
        Long like,
        Boolean isLike
) {
    public static ArticleItem of(Article article, Long like, Boolean isLike) {
        return new ArticleItem(article.getId(), article.getUser().getNickname(), article.getTitle(), article.getContent(), article.getView(), article.getCreatedAt(), like, isLike);
    }
}
