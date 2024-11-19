package com.ssafy.miro.attraction.presentation;

import com.ssafy.miro.attraction.application.AttractionService;
import com.ssafy.miro.attraction.application.response.AttractionItem;
import com.ssafy.miro.attraction.application.response.AttractionLikeItem;
import com.ssafy.miro.common.ApiResponse;
import com.ssafy.miro.common.code.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<ApiResponse<List<AttractionItem>>> getAttractions(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok().body(ApiResponse.onSuccess(attractionService.selectAllAttraction(pageable)));
    }

    @GetMapping("{no}")
    public AttractionItem getAttraction(@PathVariable("no") Integer no) {
        return attractionService.selectAttractionByNo(no);
    }

    @PatchMapping("/like/{no}")
    public ResponseEntity<ApiResponse<AttractionLikeItem>> setLikeAttraction(@PathVariable("no") Integer no) {
        return null;
    }

    @PatchMapping("/unlike/{no}")
    public ResponseEntity<ApiResponse<AttractionLikeItem>> setDislikeAttraction(@PathVariable("no") Long no) {
        return null;
    }
}
