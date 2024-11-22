package com.ssafy.miro.attraction.domain.repository;

import com.ssafy.miro.attraction.application.response.AttractionListItem;
import com.ssafy.miro.attraction.domain.dto.AttractionSearchFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AttractionRepositoryCustom {
    Page<AttractionListItem> findAttractions(AttractionSearchFilter filter, Pageable pageable);
}
