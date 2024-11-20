package com.ssafy.miro.attraction.domain.item;

import com.ssafy.miro.attraction.domain.Attraction;

public record AttractionItem(
        String title,
        String firstImage2,
        String addr1
) {
    public static AttractionItem of(Attraction attraction) {
        return new AttractionItem(attraction.getTitle(), attraction.getFirstImage2(), attraction.getAddr1());
    }
}
