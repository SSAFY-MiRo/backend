package com.ssafy.miro.attraction.application.response;

public record AttractionLikeItem(
        Long totalCount,
        boolean isLiked
) {
    public static AttractionLikeItem of(Long totalCount, boolean isLiked) {
        return new AttractionLikeItem(totalCount, isLiked);
    }
}
