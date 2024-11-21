package com.ssafy.miro.attraction.application.response;

import com.ssafy.miro.attraction.domain.Attraction;

public record AttractionListItem(
        Integer contentTypeId,
        Long view,
        String title,
        String firstImage1,
        String addr1,
        String homepage,
        String overview
) {
    public static AttractionListItem of(Attraction attraction) {
        return new AttractionListItem(
                attraction.getContentType().getId(),
                attraction.getView(),
                attraction.getTitle(),
                attraction.getFirstImage1(),
                attraction.getAddr1(),
                attraction.getHomepage(),
                attraction.getOverview());
    }
}
