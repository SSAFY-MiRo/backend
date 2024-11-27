package com.ssafy.miro.plan.presentation.request;

import com.ssafy.miro.plan.domain.PlanAttraction;
import jakarta.validation.constraints.NotNull;

public record PlanLocations(
        @NotNull Integer attractionsNo,
        @NotNull Integer day,
        @NotNull Integer order
) {
    public static PlanLocations of(PlanAttraction attraction) {
        return new PlanLocations(attraction.getAttraction().getNo(), attraction.getDays(), attraction.getOrders());
    }
}
