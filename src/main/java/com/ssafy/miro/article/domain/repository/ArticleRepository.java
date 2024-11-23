package com.ssafy.miro.article.domain.repository;

import com.ssafy.miro.article.domain.Article;
import com.ssafy.miro.article.domain.ArticleCategory;
import com.ssafy.miro.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleRepositoryCustom {
    Page<Article> findAllByCategory(ArticleCategory category, Pageable pageable);
    Page<Article> findAllByCategoryAndTitleContaining(ArticleCategory category, String title, Pageable pageable);
    Page<Article> findAllByCategoryAndTitleContainingOrContentContaining(ArticleCategory category, String title, String content, Pageable pageable);

    Optional<Article> findByIdAndUser(Long id, User user);
    //writer로 찾는거 해야함
}
