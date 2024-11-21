package com.ssafy.miro.comment.application;

import com.ssafy.miro.article.domain.Article;
import com.ssafy.miro.article.domain.repository.ArticleRepository;
import com.ssafy.miro.article.exception.ArticleNotFoundException;
import com.ssafy.miro.comment.application.response.CommentItem;
import com.ssafy.miro.comment.domain.Comment;
import com.ssafy.miro.comment.domain.repository.CommentRepository;
import com.ssafy.miro.comment.exception.CommentNotFoundException;
import com.ssafy.miro.comment.presentation.request.CommentAddRequest;
import com.ssafy.miro.comment.presentation.request.CommentUpdateRequest;
import com.ssafy.miro.common.code.ErrorCode;
import com.ssafy.miro.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public Page<CommentItem> selectAllComment(Long articleId, Pageable pageable) {
        return commentRepository.findByArticleIdAndDeletedFalse(articleId, pageable).map(CommentItem::of);
    }

    public Comment insertComment(User user, CommentAddRequest comment) {
        Article article = articleRepository.findById(comment.getArticleId()).orElseThrow(() -> new ArticleNotFoundException(ErrorCode.NOT_FOUND_BOARD_ID)   );
        return commentRepository.save(comment.toComment(user, article, comment.getContent()));
    }

    public Long updateComment(User user, CommentUpdateRequest updatedComment) {
        Comment comment = commentRepository.findById(updatedComment.getId()).orElseThrow(() -> new CommentNotFoundException(ErrorCode.NOT_FOUND_COMMENT_ID));
        isValidUserAccess(user, comment);
        comment.updateComment(updatedComment.getContent());
        return comment.getId();
    }

    public void deleteComment(User user, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        isValidUserAccess(user, comment);
        comment.deleteComment();
    }

    public void isValidUserAccess(User user, Comment comment) {
        if (!user.getId().equals(comment.getUser().getId())) throw new RuntimeException();
    }
}
