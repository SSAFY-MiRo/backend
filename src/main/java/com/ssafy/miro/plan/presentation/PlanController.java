package com.ssafy.miro.plan.presentation;

import com.ssafy.miro.common.ApiResponse;
import com.ssafy.miro.plan.application.PlanService;
import com.ssafy.miro.plan.presentation.request.PlanCreateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ssafy.miro.common.code.SuccessCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/plan")
public class PlanController {

    private final PlanService planService;

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> createPlan(@Valid @RequestBody PlanCreateRequest planCreateRequest) {
        //사용자 해야함

        planService.save(planCreateRequest);
        return ResponseEntity.ok().body(ApiResponse.of(CREATE_PLAN, null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deletePlan(@PathVariable Long id) {
        planService.deletePlan(id);
        return ResponseEntity.ok().body(ApiResponse.onSuccess(null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> updatePlan(@PathVariable Long id, @RequestBody PlanCreateRequest planCreateRequest) {
        planService.updatePlan(id, planCreateRequest);
        return ResponseEntity.ok().body(ApiResponse.onSuccess(null));
    }


}
