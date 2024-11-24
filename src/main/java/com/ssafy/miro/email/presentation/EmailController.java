package com.ssafy.miro.email.presentation;

import com.ssafy.miro.common.ApiResponse;
import com.ssafy.miro.email.application.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ssafy.miro.common.code.SuccessCode.VERIFIED_EMAIL;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class EmailController {
    private final EmailService emailService;

    @GetMapping("/verify-email")
    public ResponseEntity<ApiResponse<Object>> verifyEmail(@RequestParam("token") String token) {
        emailService.verifyEmail(token);
        return ResponseEntity.ok().body(ApiResponse.of(VERIFIED_EMAIL, null));
    }

    @GetMapping("/send-email")
    public ResponseEntity<ApiResponse<Object>> test(@RequestParam String email) {
        emailService.sendVerificationEmail(email);
        return ResponseEntity.ok().body(ApiResponse.onSuccess(null));
    }
}
