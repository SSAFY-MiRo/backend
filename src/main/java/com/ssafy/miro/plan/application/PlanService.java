package com.ssafy.miro.plan.application;

import com.ssafy.miro.attraction.application.AttractionService;
import com.ssafy.miro.attraction.domain.Attraction;
import com.ssafy.miro.attraction.domain.repository.SidoRespository;
import com.ssafy.miro.common.exception.GlobalException;
import com.ssafy.miro.plan.application.response.PlanDetailResponse;
import com.ssafy.miro.plan.application.response.PlanItem;
import com.ssafy.miro.plan.domain.Plan;
import com.ssafy.miro.plan.domain.PlanAttraction;
import com.ssafy.miro.plan.domain.respository.PlanAttractionRespository;
import com.ssafy.miro.plan.domain.respository.PlanRepository;
import com.ssafy.miro.plan.presentation.request.PlanCreateRequest;
import com.ssafy.miro.plan.presentation.request.PlanInfo;
import com.ssafy.miro.plan.presentation.request.PlanLocations;
import com.ssafy.miro.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ssafy.miro.common.code.ErrorCode.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PlanService {
    private final PlanRepository planRepository;
    private final PlanAttractionRespository planAttractionRespository;
    private final AttractionService attractionService;
    private final SidoRespository sidoRespository;

    @Transactional
    public void save(User user, PlanCreateRequest planCreateRequest) {
        // Plan 생성 및 저장
        Plan newPlan = savePlan(user, planCreateRequest.planInfo());

        // PlanAttraction 리스트 생성 및 저장
        savePlanAttractions(newPlan, planCreateRequest.planLocations());
    }

    @Transactional
    public void deletePlan(User user, Long planId) {
        Plan plan = ownerPlan(user, planId);
        plan.updateDeleted();
    }

    @Transactional
    public void updatePlan(User user, Long planId, PlanCreateRequest planCreateRequest) {
        Plan plan = ownerPlan(user, planId);
        planAttractionRespository.deleteByPlan(plan);

        // PlanAttraction 리스트 생성 및 저장
        savePlanAttractions(plan, planCreateRequest.planLocations());
    }

    public PlanDetailResponse getPlan(User user, Long planId) {
        Plan plan = ownerPlan(user, planId);
        List<PlanAttraction> planAttractions=planAttractionRespository.findAllByPlan(plan);
        return PlanDetailResponse.of(plan,planAttractions);
    }

    public List<PlanItem> getPlans(User user){
        return planRepository.findAllByUser(user).stream().map(PlanItem::of).toList();
    }

    private Plan savePlan(User user, PlanInfo planInfo) {
        return planRepository.save(Plan.of(user, planInfo));
    }

    private void savePlanAttractions(Plan newPlan, List<PlanLocations> locationInfos) {
        List<PlanAttraction> planAttractions = locationInfos.stream()
                .map(locationInfo -> toPlanAttraction(newPlan, locationInfo))
                .toList();

        planAttractionRespository.saveAll(planAttractions);
    }

    private Plan ownerPlan(User user, Long planId){
        Plan plan=findPlanById(planId);
        if(!plan.getUser().equals(user)){
            throw new GlobalException(NOT_OWNER_PLAN);
        }
        return plan;
    }

    private Plan findPlanById(Long planId) {
        return planRepository.findById(planId).orElseThrow(() -> new GlobalException(NOT_FOUND_PLAN_ID));
    }

    private PlanAttraction toPlanAttraction(Plan newPlan, PlanLocations locationInfo) {
        Attraction attraction = attractionService.findAttractionById(locationInfo.attractionsNo());
        return new PlanAttraction(newPlan, attraction, locationInfo.day(), locationInfo.order());
    }
}
