package com.ssafy.miro.plan.presentation.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record PlanInfo(
        @NotBlank String title,
        @NotBlank String location,
        @NotNull Date startDate,
        @NotNull Date endDate
) {
}
