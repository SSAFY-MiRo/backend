package com.ssafy.miro.attraction.domain.item;

import com.ssafy.miro.attraction.domain.Sido;
import com.ssafy.miro.plan.domain.PlanAttraction;

import java.util.List;

public record SidoItem(
        Integer sidoCode
) {
    public static SidoItem of(Sido sido) {
        return new SidoItem(sido.getCode());
    }

    public static SidoItem fromPlanAttractions(List<PlanAttraction> planAttractions){
        return of(planAttractions.get(0).getAttraction().getSido());
    }
}
