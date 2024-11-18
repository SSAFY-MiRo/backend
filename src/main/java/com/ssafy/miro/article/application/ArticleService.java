package com.ssafy.miro.article.application;

import com.ssafy.miro.article.application.response.ArticleItems;
import com.ssafy.miro.article.domain.Article;
import com.ssafy.miro.article.domain.repository.ArticleRepository;
import com.ssafy.miro.article.exception.ArticleNotFoundException;
import com.ssafy.miro.article.presentation.request.ArticleRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ssafy.miro.common.code.ErrorCode.NOT_FOUND_BOARD_ID;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Transactional
    public Long save(ArticleRequest articleRequest) {
        Article newArticle = articleRepository.save(new Article(articleRequest.title(), articleRequest.content()));
        return newArticle.getId();
    }

    public List<ArticleItems> getBoards() {
        return articleRepository.findAll().stream().map(ArticleItems::of).toList();
    }

    public ArticleItems getBoard(Long id) {
        return articleRepository.findById(id).map(ArticleItems::of)
                .orElseThrow(()->new ArticleNotFoundException(NOT_FOUND_BOARD_ID));
    }

    @Transactional
    public void updateBoard(Long id, ArticleRequest articleRequest) {
        Article article = articleRepository.findById(id).orElseThrow(()->new ArticleNotFoundException(NOT_FOUND_BOARD_ID));
        article.update(articleRequest.content(), article.getTitle());
    }

    @Transactional
    public void deleteBoard(Long id) {
        articleRepository.deleteById(id);
    }
}
