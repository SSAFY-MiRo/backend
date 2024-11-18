package com.ssafy.miro.article.domain.repository;

import com.ssafy.miro.article.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
