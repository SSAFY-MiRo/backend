package com.ssafy.miro.plan.presentation.request;

import jakarta.validation.constraints.NotNull;

public record PlanLocations(
        @NotNull Integer attractionsNo,
        @NotNull Integer day,
        @NotNull Integer order
) {
}
