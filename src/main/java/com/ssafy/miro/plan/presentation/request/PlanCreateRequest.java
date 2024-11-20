package com.ssafy.miro.plan.presentation.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PlanCreateRequest(
    @NotNull PlanInfo planInfo,
    @NotNull List<PlanLocations> planLocations
) {
}
