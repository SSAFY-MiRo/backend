package com.ssafy.miro.comment.presentation;

import com.ssafy.miro.comment.application.CommentService;
import com.ssafy.miro.comment.application.response.CommentItem;
import com.ssafy.miro.comment.presentation.request.CommentAddRequest;
import com.ssafy.miro.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static com.ssafy.miro.common.code.SuccessCode.*;

@Slf4j
@RestController
@RequestMapping("api/vi/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ApiResponse<CommentItem>> addComment(@RequestBody CommentAddRequest addRequest) {
        Long newCommentId = 1L;
        return ResponseEntity.created(URI.create("/api/v1/comment/" + newCommentId)).body(ApiResponse.of(CREATED_COMMENT, null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CommentItem>>> getComments(
            @RequestParam("articleId") Long articleId,
            @PageableDefault(size = 10, page = 0) Pageable pageable
    ) {
        return ResponseEntity.ok().body(ApiResponse.onSuccess(commentService.selectAllComment(articleId, pageable)));
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<Object>> updateComment(@PathVariable Long id, @RequestBody CommentItem commentItem) {
        return ResponseEntity.ok().body(ApiResponse.of(UPDATE_COMMENT, id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<Object>> deleteComment(@PathVariable Long id) {
        return ResponseEntity.ok().body(ApiResponse.of(DELETE_COMMENT, id));
    }
}
