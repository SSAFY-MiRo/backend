package com.ssafy.miro.plan.domain.respository;

import com.ssafy.miro.plan.domain.Plan;
import com.ssafy.miro.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    List<Plan> findAllByUser(User user);
}
