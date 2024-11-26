package com.ssafy.miro.ai.application;

import com.ssafy.miro.attraction.application.response.AttractionListItem;
import com.ssafy.miro.attraction.domain.Attraction;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

public record AttractionList (
    Integer no,
    String title,
    String type
){
//    public static String toString(AttractionListItem attraction) {
//        return "{ id: " + attraction.no() + ", title: " + attraction.title() + ", type: "+ attraction.contentType() + "}";
//    }
    public static String toString(AttractionListItem attraction) {
        return new AttractionList(attraction.no(), attraction.title(), attraction.contentType()).toString();
    }
}
