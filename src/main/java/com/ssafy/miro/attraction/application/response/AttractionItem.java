package com.ssafy.miro.attraction.application.response;

import com.ssafy.miro.attraction.domain.Attraction;

public record AttractionItem(
        Integer contentTypeId,
        Long view,
        String title,
        String firstImage1,
        String addr1,
        String homepage,
        String overview
) {
    public static AttractionItem of(Attraction attraction) {
        return new AttractionItem(
                attraction.getContentType().getId(),
                attraction.getView(),
                attraction.getTitle(),
                attraction.getFirstImage1(),
                attraction.getAddr1(),
                attraction.getHomepage(),
                attraction.getOverview());
    }
}
