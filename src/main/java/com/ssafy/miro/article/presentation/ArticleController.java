package com.ssafy.miro.article.presentation;

import com.ssafy.miro.article.application.ArticleService;
import com.ssafy.miro.article.application.response.ArticleItem;
import com.ssafy.miro.article.application.response.ArticleItems;
import com.ssafy.miro.article.application.response.ArticleLikeItem;
import com.ssafy.miro.article.domain.ArticleCategory;
import com.ssafy.miro.article.domain.ArticleSearchType;
import com.ssafy.miro.article.presentation.request.ArticleCreateRequest;
import com.ssafy.miro.article.presentation.request.ArticleUpdateRequest;
import com.ssafy.miro.common.ApiResponse;
import com.ssafy.miro.common.auth.Auth;
import com.ssafy.miro.common.auth.NonEssential;
import com.ssafy.miro.user.domain.User;
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

import static com.ssafy.miro.common.code.SuccessCode.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/article")
public class ArticleController {
    private final ArticleService articleService;
    @PostMapping
    public ResponseEntity<ApiResponse<Object>> createBoard(@Auth User user, @Valid @RequestBody ArticleCreateRequest articleCreateRequest) {
        Long id = articleService.save(user, articleCreateRequest);
        return ResponseEntity.created( URI.create("/article/"+id)).body(ApiResponse.of(CREATE_BOARD, id));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ArticleItems>>> getBoards(@RequestParam(name = "category") ArticleCategory articleCategory,
                                                                     @RequestParam(name = "search", required = false) String search,
                                                                     @RequestParam(name = "search-type", required = false) ArticleSearchType searchType,
                                                                     @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok().body(ApiResponse.onSuccess(articleService.getBoards(articleCategory, search, searchType, pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ArticleItem>> getBoardById(@NonEssential User user, @PathVariable Long id) {
        return ResponseEntity.ok().body(ApiResponse.onSuccess(articleService.getBoardDetail(user, id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> updateBoard(@Auth User user, @PathVariable Long id, @RequestBody ArticleUpdateRequest articleUpdateRequest) {
        articleService.updateBoard(user, id, articleUpdateRequest);
        return ResponseEntity.ok().body(ApiResponse.of(UPDATE_BOARD, null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteBoard(@Auth User user, @PathVariable Long id) {
        articleService.deleteBoard(user, id);
        return ResponseEntity.ok().body(ApiResponse.of(DELETE_BOARD, null));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<ArticleLikeItem>> updateLike(@Auth User user, @PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.onSuccess(articleService.updateLike(user, id)));
    }


}
