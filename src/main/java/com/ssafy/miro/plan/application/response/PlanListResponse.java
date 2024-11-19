package com.ssafy.miro.plan.application.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.miro.plan.domain.Plan;
import jakarta.persistence.JoinColumn;

import java.util.Date;

public record PlanListResponse(
        Long id,
        String title,
        @JsonFormat(pattern = "yyyy.mm.dd")
        Date startDate,
        @JsonFormat(pattern = "yyyy.mm.dd")
        Date endDate
) {
    public static PlanListResponse of(Plan plan) {
        return new PlanListResponse(plan.getId(), plan.getTitle(), plan.getStartDate(),plan.getEndDate());
    }
}
