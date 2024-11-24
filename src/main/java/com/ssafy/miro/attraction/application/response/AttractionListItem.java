package com.ssafy.miro.attraction.application.response;

import com.ssafy.miro.attraction.domain.Attraction;

public record AttractionListItem(
        Integer no,
        String title,
        String contentType,
        Long view,
        String firstImage1,
        String addr1,
        String homepage,
        String overview
) {
    public static AttractionListItem of(Attraction attraction) {
        return new AttractionListItem(
                attraction.getNo(),
                attraction.getTitle(),
                attraction.getContentType().getName(),
                attraction.getView(),
                attraction.getFirstImage1(),
                attraction.getAddr1(),
                attraction.getHomepage(),
                attraction.getOverview());
    }
}
