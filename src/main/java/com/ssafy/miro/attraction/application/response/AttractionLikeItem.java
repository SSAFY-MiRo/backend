package com.ssafy.miro.attraction.application.response;

import com.ssafy.miro.attraction.domain.Attraction;
import com.ssafy.miro.attraction.domain.AttractionLike;

import java.util.List;

public record AttractionLikeItem(
        Long totalCount,
        boolean isLiked
) {
    public static AttractionLikeItem of(Long totalCount, boolean isLiked) {
        return new AttractionLikeItem(totalCount, isLiked);
    }
}
