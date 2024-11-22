package com.ssafy.miro.attraction.domain.dto;

import java.util.List;

public record AttractionSearchFilter(String keyword, Integer sido, List<Integer> guguns, List<Integer> attractionType) {

    public static AttractionSearchFilter of(String keyword, Integer sido, List<Integer> guguns, List<Integer> attractionType) {
        return new AttractionSearchFilter(keyword, sido, guguns, attractionType);
    }
}
