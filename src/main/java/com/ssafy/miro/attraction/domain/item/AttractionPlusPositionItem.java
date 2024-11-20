package com.ssafy.miro.attraction.domain.item;

import com.ssafy.miro.attraction.domain.Attraction;
import com.ssafy.miro.plan.domain.PlanAttraction;

import java.math.BigDecimal;
import java.util.List;

public record AttractionPlusPositionItem(
        String title,
        String firstImage2,
        String addr1,
        BigDecimal latitude,
        BigDecimal longitude
) {
    public static AttractionPlusPositionItem of(Attraction attraction) {
        return new AttractionPlusPositionItem(
                attraction.getTitle(), attraction.getFirstImage2(), attraction.getAddr1(), attraction.getLatitude(), attraction.getLongitude()
        );
    }

    public static List<AttractionPlusPositionItem> toAttractionPlusPositionItems(List<PlanAttraction> planAttractions) {
        return planAttractions.stream().map(
                (attraction) -> AttractionPlusPositionItem.of(attraction.getAttraction())
        ).toList();
    }
}
