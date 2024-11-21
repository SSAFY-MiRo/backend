package com.ssafy.miro.attraction.application;

import com.ssafy.miro.attraction.domain.Attraction;
import com.ssafy.miro.attraction.domain.exception.AttractionNotFoundException;
import com.ssafy.miro.attraction.domain.repository.AttractionRespository;
import com.ssafy.miro.common.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AttractionService {
    private final AttractionRespository attractionRespository;

    public Attraction findAttractionById(Integer attractionId) {
        return attractionRespository.findById(attractionId)
                .orElseThrow(() -> new AttractionNotFoundException(ErrorCode.NOT_FOUND_ATTRACTION_ID));
    }
}
