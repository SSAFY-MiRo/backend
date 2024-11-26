package com.ssafy.miro.attraction.presentation;

import com.ssafy.miro.article.application.response.ArticleItem;
import com.ssafy.miro.attraction.application.AttractionService;
import com.ssafy.miro.attraction.application.response.AttractionDetailItem;
import com.ssafy.miro.attraction.application.response.AttractionListItem;
import com.ssafy.miro.attraction.application.response.AttractionLikeItem;
import com.ssafy.miro.attraction.domain.Attraction;
import com.ssafy.miro.attraction.domain.Gugun;
import com.ssafy.miro.attraction.domain.dto.AttractionSearchFilter;
import com.ssafy.miro.attraction.domain.item.GugunItem;
import com.ssafy.miro.common.ApiResponse;
import com.ssafy.miro.common.auth.Auth;
import com.ssafy.miro.common.auth.NonEssential;
import com.ssafy.miro.common.code.SuccessCode;
import com.ssafy.miro.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/attraction")
@RequiredArgsConstructor
public class AttractionController {
    private final AttractionService attractionService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<AttractionListItem>>> getAttractions(
            @PageableDefault Pageable pageable,
            @RequestParam(value = "sido", required = false) Integer sido,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "gugun", required = false) List<String> guguns,
            @RequestParam(value = "attractionType", required = false) List<Integer> attractionType
            ) {
        log.info("sido = {}", sido);
        log.info("page = {}", pageable);
        log.info("guguns = {}", guguns);
        log.info("attractionType = {}", attractionType);
        return ResponseEntity.ok().body(ApiResponse.onSuccess(
                attractionService.selectAllAttractions(
                        pageable, AttractionSearchFilter.of(keyword, sido, guguns, attractionType)
                )
        ));
    }

    @GetMapping("{no}")
    public ResponseEntity<ApiResponse<AttractionDetailItem>> getAttraction(@NonEssential User user, @PathVariable("no") Integer no) {
        return ResponseEntity.ok().body(ApiResponse.onSuccess(attractionService.getAttractionDetail(user, no)));
    }

    @PostMapping("/like/{no}")
    public ResponseEntity<ApiResponse<AttractionLikeItem>> setLikeAttraction(@Auth User user, @PathVariable("no") Integer no) {
        return ResponseEntity.ok().body(ApiResponse.of(SuccessCode.OK, attractionService.likeHandler(user, no)));
    }

    @GetMapping("/gungu")
    public ResponseEntity<ApiResponse<List<GugunItem>>> getGunGus(@RequestParam("sido") Integer sidoCode) {
        return ResponseEntity.ok().body(ApiResponse.onSuccess(attractionService.getAttractionGungus(sidoCode)));
    }

//    @GetMapping("/popular")
//    public ResponseEntity<ApiResponse<List<GugunItem>>> getPopularAttractions() {
//        return ResponseEntity.ok().body(ApiResponse.onSuccess(attractionService.getAttractionGungus(sidoCode)));
//    }
}
