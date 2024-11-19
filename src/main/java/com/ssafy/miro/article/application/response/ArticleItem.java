package com.ssafy.miro.article.application.response;

import com.ssafy.miro.article.domain.Article;

import java.time.LocalDateTime;

public record ArticleItem(
        Long id,
//        String nick,
        String title,
        String content,
        Long view,
        LocalDateTime createTime,
        Long like,
        Boolean isLike
) {
    public static ArticleItem of(Article article, Long like, Boolean isLike) {
        return new ArticleItem(article.getId(), article.getTitle(), article.getContent(), article.getView(), article.getCreatedAt(), like, isLike);
    }
}
