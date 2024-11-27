package com.ssafy.miro.attraction.domain.item;

import com.ssafy.miro.attraction.domain.Attraction;
import com.ssafy.miro.plan.domain.PlanAttraction;

import java.math.BigDecimal;
import java.util.List;

public record AttractionPlusPositionItem(
        Integer no,
        String title,
        String firstImage1,
        String addr1,
        BigDecimal latitude,
        BigDecimal longitude,
        Integer day,
        Integer order
) {
    public static AttractionPlusPositionItem of(Attraction attraction, PlanAttraction planAttraction) {
        return new AttractionPlusPositionItem(
                attraction.getNo(), attraction.getTitle(), attraction.getFirstImage1(), attraction.getAddr1(), attraction.getLatitude(), attraction.getLongitude(), planAttraction.getDays(), planAttraction.getOrders()
        );
    }

    public static List<AttractionPlusPositionItem> toAttractionPlusPositionItems(List<PlanAttraction> planAttractions) {
        return planAttractions.stream().map(
                (attraction) -> AttractionPlusPositionItem.of(attraction.getAttraction(), attraction)
        ).toList();
    }
}
