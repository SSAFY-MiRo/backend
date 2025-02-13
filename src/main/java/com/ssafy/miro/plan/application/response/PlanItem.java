package com.ssafy.miro.plan.application.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.miro.plan.domain.Plan;

import java.time.LocalDateTime;
import java.util.Date;

public record PlanItem(
        Long id,
        String title,
        @JsonFormat(pattern = "yyyy.mm.dd")
        Date startDate,
        @JsonFormat(pattern = "yyyy.mm.dd")
        Date endDate,
        LocalDateTime createAt
) {
    public static PlanItem of(Plan plan) {
        return new PlanItem(plan.getId(), plan.getTitle(), plan.getStartDate(),plan.getEndDate(), plan.getCreatedAt());
    }
}
