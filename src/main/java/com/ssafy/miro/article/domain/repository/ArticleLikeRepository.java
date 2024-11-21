package com.ssafy.miro.article.domain.repository;

import com.ssafy.miro.article.domain.Article;
import com.ssafy.miro.article.domain.ArticleLike;
import com.ssafy.miro.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long> {
    Long countByArticle(Article article);
    Optional<ArticleLike> findByArticleAndUser(Article article, User user);

}
