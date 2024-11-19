package com.ssafy.miro.article.presentation;

import com.ssafy.miro.article.application.ArticleService;
import com.ssafy.miro.article.application.response.ArticleItems;
import com.ssafy.miro.article.domain.ArticleCategory;
import com.ssafy.miro.article.domain.ArticleSearchType;
import com.ssafy.miro.article.presentation.request.ArticleCreateRequest;
import com.ssafy.miro.article.presentation.request.ArticleUpdateRequest;
import com.ssafy.miro.common.ApiResponse;
import jakarta.validation.Valid;
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
@RequiredArgsConstructor
@RequestMapping("/board")
public class ArticleController {
    private final ArticleService articleService;
    @PostMapping
    public ResponseEntity<ApiResponse<Object>> createBoard(@Valid @RequestBody ArticleCreateRequest articleCreateRequest) {
        Long newBoardId = articleService.save(articleCreateRequest);
        return ResponseEntity.created( URI.create("/board/"+newBoardId)).body(ApiResponse.of(CREATE_BOARD, null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ArticleItems>>> getBoards(@RequestParam(name = "category") ArticleCategory articleCategory,
                                                                     @RequestParam(name = "search", required = false) String search,
                                                                     @RequestParam(name = "search-type", required = false) ArticleSearchType searchType,
                                                                     @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok().body(ApiResponse.onSuccess(articleService.getBoards(articleCategory, search, searchType, pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ArticleItems>> getBoardById(@PathVariable Long id) {
        return ResponseEntity.ok().body(ApiResponse.onSuccess(articleService.getBoard(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> updateBoard(@PathVariable Long id, @RequestBody ArticleUpdateRequest articleUpdateRequest) {
        articleService.updateBoard(id, articleUpdateRequest);
        return ResponseEntity.ok().body(ApiResponse.of(UPDATE_BOARD, null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteBoard(@PathVariable Long id) {
        articleService.deleteBoard(id);
        return ResponseEntity.ok().body(ApiResponse.of(DELETE_BOARD, null));
    }

}
