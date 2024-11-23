package com.ssafy.miro.article.domain.repository;

import com.ssafy.miro.article.application.response.ArticleItems;
import com.ssafy.miro.article.domain.ArticleCategory;
import com.ssafy.miro.article.domain.ArticleSearchType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleRepositoryCustom {
    public Page<ArticleItems> getArticleItems(ArticleCategory articleCategory, String search, ArticleSearchType articleSearchType, Pageable pageable);
}
