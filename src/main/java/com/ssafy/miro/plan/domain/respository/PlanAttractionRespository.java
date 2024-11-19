package com.ssafy.miro.plan.domain.respository;

import com.ssafy.miro.plan.domain.Plan;
import com.ssafy.miro.plan.domain.PlanAttraction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanAttractionRespository extends JpaRepository<PlanAttraction, Integer> {
    void deleteByPlan(Plan plan);
}
