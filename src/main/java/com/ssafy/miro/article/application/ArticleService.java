package com.ssafy.miro.article.application;

import com.ssafy.miro.article.application.response.ArticleItem;
import com.ssafy.miro.article.application.response.ArticleItems;
import com.ssafy.miro.article.application.response.ArticleLikeItem;
import com.ssafy.miro.article.domain.Article;
import com.ssafy.miro.article.domain.ArticleCategory;
import com.ssafy.miro.article.domain.ArticleLike;
import com.ssafy.miro.article.domain.ArticleSearchType;
import com.ssafy.miro.article.domain.repository.ArticleLikeRepository;
import com.ssafy.miro.article.domain.repository.ArticleRepository;
import com.ssafy.miro.article.presentation.request.ArticleCreateRequest;
import com.ssafy.miro.article.presentation.request.ArticleUpdateRequest;
import com.ssafy.miro.common.exception.GlobalException;
import com.ssafy.miro.user.domain.User;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ssafy.miro.common.code.ErrorCode.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleLikeRepository articleLikeRepository;

    @Transactional
    public Long save(User user, ArticleCreateRequest articleCreateRequest) {
        Article newArticle = articleRepository.save(new Article(user, articleCreateRequest.title(), articleCreateRequest.content(), articleCreateRequest.category()));
        return newArticle.getId();
    }

    public Page<ArticleItems> getBoards(ArticleCategory articleCategory, String search, ArticleSearchType articleSearchType, Pageable pageable) {
        return articleRepository.getArticleItems(articleCategory, search, articleSearchType, pageable);
    }

    @Transactional
    public ArticleItem getBoardDetail(User user, Long id){
        Article article = findById(id);
        article.increaseView();
        ArticleLikeItem articleLikeAndLiked = getArticleLikeAndLiked(article, user);
        return ArticleItem.of(article,articleLikeAndLiked.likeCount(),articleLikeAndLiked.liked());
    }


    @Transactional
    public void updateBoard(User user, Long id, ArticleUpdateRequest articleUpdateRequest) {
        Article article = checkOwnerArticle(id, user);
        article.update(articleUpdateRequest.title(), articleUpdateRequest.content());
    }

    @Transactional
    public void deleteBoard(User user, Long id) {
        Article article = checkOwnerArticle(id, user);
        article.updateDeleted();
    }

    private Article findById(Long id) {
        return articleRepository.findById(id).orElseThrow(()->new GlobalException(NOT_FOUND_BOARD_ID));
    }

    private Article checkOwnerArticle(Long id, User user) {
        Article article = findById(id);
        if(!article.getUser().equals(user)) throw new GlobalException(NOT_OWNER_ARTICLE);
        return article;
    }

    @Transactional
    public ArticleLikeItem updateLike(User user, Long id) {
        Article article = findById(id);
        articleLikeRepository.findByArticleAndUser(article, user).ifPresentOrElse(
                articleLikeRepository::delete,
                ()->articleLikeRepository.save(new ArticleLike(user, article))
        );
        return getArticleLikeAndLiked(article, user);
    }

    private ArticleLikeItem getArticleLikeAndLiked(Article article, User user) {
        Long likeCount=getArticleLike(article);
        boolean isLike = isLikeByUser(article,user);
        return new ArticleLikeItem(likeCount,isLike);
    }

    private Long getArticleLike(Article article) {
        return articleLikeRepository.countByArticle(article);
    }

    private boolean isLikeByUser(Article article, User user) {
        return user != null && articleLikeRepository.findByArticleAndUser(article, user).isPresent();
    }
}
