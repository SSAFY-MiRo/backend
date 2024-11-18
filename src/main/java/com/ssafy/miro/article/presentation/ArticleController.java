package com.ssafy.miro.article.presentation;

import com.ssafy.miro.article.application.ArticleService;
import com.ssafy.miro.article.application.response.ArticleItems;
import com.ssafy.miro.article.presentation.request.ArticleRequest;
import com.ssafy.miro.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static com.ssafy.miro.common.code.SuccessCode.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class ArticleController {
    private final ArticleService articleService;
    @PostMapping
    public ResponseEntity<ApiResponse<Object>> createBoard(@Valid @RequestBody ArticleRequest articleRequest) {
        Long newBoardId = articleService.save(articleRequest);
        return ResponseEntity.created( URI.create("/board/"+newBoardId)).body(ApiResponse.of(CREATE_BOARD, null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ArticleItems>>> getBoards() {
        return ResponseEntity.ok().body(ApiResponse.onSuccess(articleService.getBoards()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ArticleItems>> getBoardById(@PathVariable Long id) {
        return ResponseEntity.ok().body(ApiResponse.onSuccess(articleService.getBoard(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> updateBoard(@PathVariable Long id, @RequestBody ArticleRequest articleRequest) {
        articleService.updateBoard(id, articleRequest);
        return ResponseEntity.ok().body(ApiResponse.of(UPDATE_BOARD, null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteBoard(@PathVariable Long id) {
        articleService.deleteBoard(id);
        return ResponseEntity.ok().body(ApiResponse.of(DELETE_BOARD, null));
    }

}
