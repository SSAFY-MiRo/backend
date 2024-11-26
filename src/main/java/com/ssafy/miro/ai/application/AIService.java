package com.ssafy.miro.ai.application;

import com.ssafy.miro.attraction.application.AttractionService;
import com.ssafy.miro.attraction.application.response.AttractionListItem;
import com.ssafy.miro.attraction.domain.Attraction;
import com.ssafy.miro.attraction.domain.Sido;
import com.ssafy.miro.attraction.domain.dto.AttractionSearchFilter;
import com.ssafy.miro.attraction.domain.repository.AttractionRepository;
import com.ssafy.miro.attraction.domain.repository.SidoRespository;
import com.ssafy.miro.common.ai.PromptLoader;
import io.github.flashvayne.chatgpt.dto.chat.MultiChatMessage;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final AttractionService attractionService;

    public String getChatResponse(Integer sidoId, String prompt){
        Sido sido = sidoRespository.findByCode(sidoId);
        Page<AttractionListItem> attractionListItems = attractionService.selectAllAttractions(Pageable.ofSize(450), new AttractionSearchFilter(null, sidoId, null, null));
        // Attraction 객체의 이름을 info 리스트에 추가
        List<String> info = attractionListItems.stream()
                .map(AttractionList::toString) // attraction 객체의 필드를 가져옴
                .toList();
        System.out.println(info);

        List<MultiChatMessage> messages=new ArrayList<>();
        messages.add(new MultiChatMessage("system",promptLoader.loadSystemPrompt()));
        messages.add(new MultiChatMessage("system","리스트는 다음과 같아\n"+ info));
        messages.add(new MultiChatMessage("user",prompt));
        messages.add(new MultiChatMessage("system",promptLoader.loadUserPrompt()));
        return chatgptService.multiChat(messages);
    }
}
