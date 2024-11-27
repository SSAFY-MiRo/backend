package com.ssafy.miro.ai.presentation;
import com.ssafy.miro.ai.application.AIService;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/ai")
public class AIController {
    private final AIService aiService;

    @GetMapping("/recommend")
    public String recommendPlan(@RequestParam Integer sidoId, @RequestParam String prompt) {
        return aiService.getChatResponse(sidoId, prompt);
    }
}
