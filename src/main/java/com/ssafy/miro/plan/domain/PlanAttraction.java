package com.ssafy.miro.plan.domain;

import com.ssafy.miro.attraction.domain.Attraction;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity(name = "plan_attractions")
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
public class PlanAttraction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attraction_no")
    private Attraction attraction;

    private Integer days;
    private Integer orders;

    public PlanAttraction(Plan newPlan, Attraction findAttraction, Integer day, Integer order) {
        this.plan = newPlan;
        this.attraction = findAttraction;
        this.days = day;
        this.orders = order;
    }
}
