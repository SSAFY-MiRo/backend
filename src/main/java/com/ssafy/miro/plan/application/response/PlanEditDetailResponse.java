package com.ssafy.miro.plan.application.response;

import com.ssafy.miro.plan.presentation.request.PlanInfo;
import com.ssafy.miro.plan.presentation.request.PlanLocations;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PlanEditDetailResponse(
    PlanInfo planInfo,
    List<PlanLocations> planLocations
) {
    public static PlanEditDetailResponse of(PlanInfo planInfo, List<PlanLocations> planLocations) {
        return new PlanEditDetailResponse(planInfo, planLocations);
    }
}
