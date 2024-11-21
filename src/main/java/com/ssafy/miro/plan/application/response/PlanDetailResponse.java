package com.ssafy.miro.plan.application.response;

import com.ssafy.miro.attraction.domain.Attraction;
import com.ssafy.miro.attraction.domain.Sido;
import com.ssafy.miro.attraction.domain.item.AttractionItem;
import com.ssafy.miro.attraction.domain.item.AttractionPlusPositionItem;
import com.ssafy.miro.attraction.domain.item.SidoItem;
import com.ssafy.miro.plan.domain.Plan;
import com.ssafy.miro.plan.domain.PlanAttraction;

import java.util.List;

public record PlanDetailResponse(
        PlanItem planItem,
        List<AttractionPlusPositionItem> attractionPlusPositionItem,
        SidoItem sidoItem
) {
    public static PlanDetailResponse of(Plan plan, List<PlanAttraction> planAttractions) {
        return new PlanDetailResponse(PlanItem.of(plan), AttractionPlusPositionItem.toAttractionPlusPositionItems(planAttractions), SidoItem.fromPlanAttractions(planAttractions));
    }
}
