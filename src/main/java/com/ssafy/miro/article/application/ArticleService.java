package com.ssafy.miro.article.application;

import com.ssafy.miro.article.application.response.ArticleItems;
import com.ssafy.miro.article.domain.Article;
import com.ssafy.miro.article.domain.ArticleCategory;
import com.ssafy.miro.article.domain.ArticleSearchType;
import com.ssafy.miro.article.domain.repository.ArticleRepository;
import com.ssafy.miro.article.exception.ArticleNotFoundException;
import com.ssafy.miro.article.presentation.request.ArticleCreateRequest;
import com.ssafy.miro.article.presentation.request.ArticleUpdateRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Long save(ArticleCreateRequest articleCreateRequest) {
        Article newArticle = articleRepository.save(new Article(articleCreateRequest.title(), articleCreateRequest.content(), articleCreateRequest.category()));
        return newArticle.getId();
    }

    public Page<ArticleItems> getBoards(ArticleCategory articleCategory, String search, ArticleSearchType articleSearchType, Pageable pageable) {
        if(search==null){
            return articleRepository.findAllByCategory(articleCategory, pageable).map(ArticleItems::of);
        } else if(articleSearchType.equals(ArticleSearchType.TITLE)){
            return articleRepository.findAllByCategoryAndTitleContaining(articleCategory, search, pageable).map(ArticleItems::of);
        } else if (articleSearchType.equals(ArticleSearchType.ANY)) {
            return articleRepository.findAllByCategoryAndTitleContainingOrContentContaining(articleCategory, search, search, pageable).map(ArticleItems::of);
        } else { //사용자 검색도 해야 함
            return null;
        }

    }

    public ArticleItems getBoard(Long id) {
        return articleRepository.findById(id).map(ArticleItems::of)
                .orElseThrow(()->new ArticleNotFoundException(NOT_FOUND_BOARD_ID));
    }

    @Transactional
    public void updateBoard(Long id, ArticleUpdateRequest articleUpdateRequest) {
        Article article = articleRepository.findById(id).orElseThrow(()->new ArticleNotFoundException(NOT_FOUND_BOARD_ID));
        article.update(articleUpdateRequest.title(), articleUpdateRequest.content());
    }

    @Transactional
    public void deleteBoard(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(()->new ArticleNotFoundException(NOT_FOUND_BOARD_ID));
        article.updateDeleted();
    }

}
