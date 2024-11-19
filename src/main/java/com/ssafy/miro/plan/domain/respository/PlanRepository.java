package com.ssafy.miro.plan.domain.respository;

import com.ssafy.miro.plan.domain.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}
