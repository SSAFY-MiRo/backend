package com.ssafy.miro.plan.presentation;

import com.ssafy.miro.common.ApiResponse;
import com.ssafy.miro.plan.application.PlanService;
import com.ssafy.miro.plan.presentation.request.PlanCreateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
