package com.ssafy.miro.plan.presentation;

import com.ssafy.miro.common.ApiResponse;
import com.ssafy.miro.common.auth.Auth;
import com.ssafy.miro.plan.application.PlanService;
import com.ssafy.miro.plan.application.response.PlanDetailResponse;
import com.ssafy.miro.plan.application.response.PlanItem;
import com.ssafy.miro.plan.presentation.request.PlanCreateRequest;
import com.ssafy.miro.user.domain.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ssafy.miro.common.code.SuccessCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/plan")
public class PlanController {

    private final PlanService planService;

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> createPlan(@Auth User user, @Valid @RequestBody PlanCreateRequest planCreateRequest) {
        planService.save(user, planCreateRequest);
        return ResponseEntity.ok().body(ApiResponse.of(CREATE_PLAN, null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deletePlan(@Auth User user, @PathVariable Long id) {
        planService.deletePlan(user, id);
        return ResponseEntity.ok().body(ApiResponse.onSuccess(null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> updatePlan(@Auth User user, @PathVariable Long id, @RequestBody PlanCreateRequest planCreateRequest) {
        planService.updatePlan(user, id, planCreateRequest);
        return ResponseEntity.ok().body(ApiResponse.onSuccess(null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> getPlan(@Auth User user, @PathVariable Long id) {
        PlanDetailResponse detailResponse = planService.getPlan(user, id);
        return ResponseEntity.ok().body(ApiResponse.onSuccess(detailResponse));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Object>> getPlans(@Auth User user) {
        List<PlanItem> plans = planService.getPlans(user);
        return ResponseEntity.ok().body(ApiResponse.onSuccess(plans));
    }

    @GetMapping("/edit/{id}")
    public ResponseEntity<ApiResponse<Object>> getPlanEditInfo(@Auth User user, @PathVariable Long id) {
        return ResponseEntity.ok().body(ApiResponse.onSuccess(planService.getPlanEditInfo(user, id)));
    }
}
