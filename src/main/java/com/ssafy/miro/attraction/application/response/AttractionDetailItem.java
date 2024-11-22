package com.ssafy.miro.attraction.application.response;

import com.ssafy.miro.attraction.domain.Attraction;

public record AttractionDetailItem(
        Integer contentTypeId,
        Long view,
        String title,
        String firstImage1,
        String addr1,
        String homepage,
        String overview,
        Long totalCount,
        boolean isLiked
) {
    public static AttractionDetailItem of(Attraction attraction, AttractionLikeItem attractionLikeItem) {
        return new AttractionDetailItem(
                attraction.getContentType().getId(),
                attraction.getView(),
                attraction.getTitle(),
                attraction.getFirstImage1(),
                attraction.getAddr1(),
                attraction.getHomepage(),
                attraction.getOverview(),
                attractionLikeItem.totalCount(),
                attractionLikeItem.isLiked()
                );
    }
}
