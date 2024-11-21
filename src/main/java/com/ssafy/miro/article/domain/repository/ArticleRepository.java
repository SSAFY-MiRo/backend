package com.ssafy.miro.article.domain.repository;

import com.ssafy.miro.article.domain.Article;
import com.ssafy.miro.article.domain.ArticleCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findAllByCategory(ArticleCategory category, Pageable pageable);
    Page<Article> findAllByCategoryAndTitleContaining(ArticleCategory category, String title, Pageable pageable);
    Page<Article> findAllByCategoryAndTitleContainingOrContentContaining(ArticleCategory category, String title, String content, Pageable pageable);
    //writer로 찾는거 해야함
}
