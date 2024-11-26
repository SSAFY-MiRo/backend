package com.ssafy.miro.ai.application;

import com.ssafy.miro.attraction.domain.Attraction;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

public record AttractionList (
    Integer no,
    String title,
    String addr
){
    public static String toString(Attraction attraction) {
        return new AttractionList(attraction.getNo(), attraction.getTitle(), attraction.getAddr1()).toString();
    }
}
