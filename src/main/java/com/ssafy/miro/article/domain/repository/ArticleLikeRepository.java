package com.ssafy.miro.article.domain.repository;

import com.ssafy.miro.article.domain.Article;
import com.ssafy.miro.article.domain.ArticleLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long> {
    Long countByArticle(Article article);

}
