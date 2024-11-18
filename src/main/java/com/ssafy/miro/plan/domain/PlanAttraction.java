package com.ssafy.miro.plan.domain;

import com.ssafy.miro.attraction.domain.Attraction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "plan_attractions")
@NoArgsConstructor
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

}
