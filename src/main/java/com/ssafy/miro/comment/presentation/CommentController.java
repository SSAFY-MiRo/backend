package com.ssafy.miro.comment.presentation;

import com.ssafy.miro.article.domain.Article;
import com.ssafy.miro.comment.application.CommentService;
import com.ssafy.miro.comment.application.response.CommentItem;
import com.ssafy.miro.comment.domain.Comment;
import com.ssafy.miro.comment.presentation.request.CommentAddRequest;
import com.ssafy.miro.comment.presentation.request.CommentUpdateRequest;
import com.ssafy.miro.common.ApiResponse;
import com.ssafy.miro.common.auth.Auth;
import com.ssafy.miro.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static com.ssafy.miro.common.code.SuccessCode.*;

@Slf4j
@RestController
@RequestMapping("api/v1/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ApiResponse<CommentItem>> addComment(@Auth User user, @RequestBody CommentAddRequest addRequest) {
        Comment comment = commentService.insertComment(user, addRequest);
        return ResponseEntity.created(URI.create("/api/v1/comment/" + comment.getId())).body(ApiResponse.of(CREATED_COMMENT, null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<CommentItem>>> getComments(
            @RequestParam("articleId") Long articleId,
            @PageableDefault(size = 10, page = 0, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok().body(ApiResponse.onSuccess(commentService.selectAllComment(articleId, pageable)));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<Object>> updateComment(@Auth User user, @RequestBody CommentUpdateRequest updateCommentRequest) {
        Long id = commentService.updateComment(user, updateCommentRequest);
        return ResponseEntity.ok().body(ApiResponse.of(UPDATE_COMMENT, id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<Object>> deleteComment(@Auth User user, @PathVariable Long id) {
        commentService.deleteComment(user, id);
        return ResponseEntity.ok().body(ApiResponse.of(DELETE_COMMENT, id));
    }
}
