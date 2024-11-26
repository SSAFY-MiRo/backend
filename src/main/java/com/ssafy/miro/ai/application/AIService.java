package com.ssafy.miro.ai.application;

import com.ssafy.miro.attraction.application.AttractionService;
import com.ssafy.miro.attraction.domain.Attraction;
import com.ssafy.miro.attraction.domain.Sido;
import com.ssafy.miro.attraction.domain.repository.AttractionRepository;
import com.ssafy.miro.attraction.domain.repository.SidoRespository;
import com.ssafy.miro.common.ai.PromptLoader;
import io.github.flashvayne.chatgpt.dto.chat.MultiChatMessage;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class AIService {
    private final ChatgptService chatgptService;
    private final PromptLoader promptLoader;
    private final AttractionRepository attractionRepository;
    private final SidoRespository sidoRespository;

    public String getChatResponse(Integer sidoId, String prompt){
        Sido sido = sidoRespository.findByCode(sidoId);
        List<Attraction> attractions = attractionRepository.findAllBySido(sido);
        // Attraction 객체의 이름을 info 리스트에 추가
        List<String> info = attractions.stream()
                .map(AttractionList::toString) // attraction 객체의 필드를 가져옴
                .toList();
        System.out.println(info);

        List<MultiChatMessage> messages=new ArrayList<>();
        messages.add(new MultiChatMessage("system",promptLoader.loadSystemPrompt()));
        messages.add(new MultiChatMessage("system",info.toString()));
        messages.add(new MultiChatMessage("user",prompt));
        return chatgptService.multiChat(messages);
    }
}
