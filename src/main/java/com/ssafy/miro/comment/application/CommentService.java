package com.ssafy.miro.comment.application;

import com.ssafy.miro.comment.application.response.CommentItems;
import com.ssafy.miro.comment.domain.Comment;
import com.ssafy.miro.comment.domain.repository.CommentRepository;
import com.ssafy.miro.comment.exception.CommentNotFoundException;
import com.ssafy.miro.common.code.ErrorCode;
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

    @Transactional(readOnly = true)
    public List<CommentItems> selectAllComment(Pageable pageable) {
        return commentRepository.findAll(pageable).stream().map(CommentItems::of).toList();
    }

    @Transactional(readOnly = true)
    public CommentItems selectComment(Long id) {
        return commentRepository.findById(id).map(CommentItems::of).orElseThrow(() -> new CommentNotFoundException(ErrorCode.NOT_FOUND_COMMENT_ID));
    }

    public void insertComment(Comment comment) {
        commentRepository.save(comment);
    }

    public Long updateComment(String modifiedContent) {
        Comment comment = commentRepository.findById(1L).orElseThrow();
        comment.updateComment(modifiedContent);
        return comment.getId();
    }

    public void deleteComment() {
        Comment comment = commentRepository.findById(1L).orElseThrow();
        // BaseEntity 메소드 통해 삭제
    }
}
