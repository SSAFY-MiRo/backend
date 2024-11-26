package com.ssafy.miro.plan.presentation.request;

import com.ssafy.miro.plan.domain.Plan;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record PlanInfo(
        @NotBlank String title,
        @NotBlank String location,
        @NotNull Date startDate,
        @NotNull Date endDate
) {
    public static PlanInfo of(Plan plan) {
        return new PlanInfo(plan.getTitle(), plan.getLocation(), plan.getStartDate(), plan.getEndDate());
    }
}
