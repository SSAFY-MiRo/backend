package com.ssafy.miro.plan.application;

import com.ssafy.miro.attraction.application.AttractionService;
import com.ssafy.miro.attraction.domain.Attraction;
import com.ssafy.miro.attraction.domain.exception.AttractionNotFoundException;
import com.ssafy.miro.attraction.domain.repository.AttractionRespository;
import com.ssafy.miro.common.code.ErrorCode;
import com.ssafy.miro.plan.domain.Plan;
import com.ssafy.miro.plan.domain.PlanAccessLevel;
import com.ssafy.miro.plan.domain.PlanAttraction;
import com.ssafy.miro.plan.domain.respository.PlanAttractionRespository;
import com.ssafy.miro.plan.domain.respository.PlanRepository;
import com.ssafy.miro.plan.exception.PlanNotFoundException;
import com.ssafy.miro.plan.presentation.request.PlanCreateRequest;
import com.ssafy.miro.plan.presentation.request.PlanInfo;
import com.ssafy.miro.plan.presentation.request.PlanLocations;
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

    @Transactional
    public void save(PlanCreateRequest planCreateRequest) {
        // Plan 생성 및 저장
        Plan newPlan = savePlan(planCreateRequest.planInfo());

        // PlanAttraction 리스트 생성 및 저장
        savePlanAttractions(newPlan, planCreateRequest.planLocations());
    }

    private Plan savePlan(PlanInfo planInfo) {
        return planRepository.save(Plan.of(planInfo));
    }

    private void savePlanAttractions(Plan newPlan, List<PlanLocations> locationInfos) {
        List<PlanAttraction> planAttractions = locationInfos.stream()
                .map(locationInfo -> toPlanAttraction(newPlan, locationInfo))
                .toList();

        planAttractionRespository.saveAll(planAttractions);
    }

    private PlanAttraction toPlanAttraction(Plan newPlan, PlanLocations locationInfo) {
        Attraction attraction = attractionService.findAttractionById(locationInfo.attractionsNo());
        return new PlanAttraction(newPlan, attraction, locationInfo.day(), locationInfo.order());
    }

    @Transactional
    public void deletePlan(Long planId) {
        Plan plan=planRepository.findById(planId).orElseThrow(()->new PlanNotFoundException(NOT_FOUND_PLAN_ID));
        plan.updateDeleted();
    }

    @Transactional
    public void updatePlan(Long id, PlanCreateRequest planCreateRequest) {
        Plan plan = planRepository.findById(id).orElseThrow(() -> new PlanNotFoundException(NOT_FOUND_PLAN_ID));
        planAttractionRespository.deleteByPlan(plan);

        // PlanAttraction 리스트 생성 및 저장
        savePlanAttractions(plan, planCreateRequest.planLocations());
    }
}
