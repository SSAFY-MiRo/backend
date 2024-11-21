package com.ssafy.miro.attraction.application;

import com.ssafy.miro.attraction.application.response.AttractionListItem;
import com.ssafy.miro.attraction.domain.Attraction;
import com.ssafy.miro.attraction.domain.dto.AttractionSearchFilter;
import com.ssafy.miro.attraction.domain.repository.AttractionRespository;
import com.ssafy.miro.common.code.ErrorCode;
import com.ssafy.miro.common.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AttractionService {
    private final AttractionRespository attractionRespository;

    public Page<AttractionListItem> selectAllAttractions(Pageable pageable, AttractionSearchFilter filter) {
        Page<AttractionListItem> result = null;

        if (filter.sido() == null) throw new GlobalException(ErrorCode.INVALID_REQUEST);
        else if (filter.guguns() == null &&
                filter.attractionType() == null &&
                filter.keyword() == null) {
            result = attractionRespository.findAllBySidoCode(filter.sido(), pageable).map(AttractionListItem::of);
        } else if (filter.guguns() == null && filter.attractionType() == null) {
            result = attractionRespository.findAllBySidoCodeAndTitleContaining(filter.sido(), filter.keyword(), pageable).map(AttractionListItem::of);
        } else if (filter.guguns() == null && filter.keyword() == null) {
            result = attractionRespository.findAllBySidoCodeAndContentTypeIn(filter.sido(), filter.attractionType(), pageable).map(AttractionListItem::of);
        } else if (filter.keyword() == null && filter.attractionType() == null) {
            result = attractionRespository.findAllBySidoCodeAndGuGunCodeIn(filter.sido(), filter.guguns(), pageable).map(AttractionListItem::of);
        } else if (filter.guguns() == null) {
            result = attractionRespository.findAllBySidoCodeAndContentTypeInAndTitleContaining(filter.sido(), filter.attractionType(), filter.keyword(), pageable).map(AttractionListItem::of);
        } else if (filter.keyword() == null) {
            result = attractionRespository.findAllBySidoCodeAndGuGunCodeInAndContentTypeIn(filter.sido(), filter.guguns(), filter.attractionType(), pageable).map(AttractionListItem::of);
        } else if (filter.attractionType() == null) {
            result = attractionRespository.findAllBySidoCodeAndGugunCodeInAndTitleContaining(filter.sido(), filter.guguns(), filter.keyword(), pageable).map(AttractionListItem::of);
        } else {
            result = attractionRespository.findAllBySidoCodeAndGuGunCodeInAndContentTypeInAndTitleContaining(
                    filter.sido(), filter.guguns(), filter.attractionType(), filter.keyword(), pageable
            ).map(AttractionListItem::of);
        }

        return result;
    }

//    public Attraction selectAttractionByAttractionNo(Integer attractionId) {
//        return attractionRespository.findById(attractionId)
//                .orElseThrow(() -> new AttractionNotFoundException(ErrorCode.NOT_FOUND_ATTRACTION_ID));
//    }
}
