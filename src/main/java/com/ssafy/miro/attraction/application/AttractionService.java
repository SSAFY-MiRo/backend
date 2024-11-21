package com.ssafy.miro.attraction.application;

import com.ssafy.miro.attraction.application.response.AttractionDetailItem;
import com.ssafy.miro.attraction.application.response.AttractionLikeItem;
import com.ssafy.miro.attraction.application.response.AttractionListItem;
import com.ssafy.miro.attraction.domain.Attraction;
import com.ssafy.miro.attraction.domain.AttractionLike;
import com.ssafy.miro.attraction.domain.dto.AttractionSearchFilter;
import com.ssafy.miro.attraction.domain.repository.AttractionLikeRepository;
import com.ssafy.miro.attraction.domain.repository.AttractionRepository;
import com.ssafy.miro.attraction.exception.AttractionNotFoundException;
import com.ssafy.miro.common.code.ErrorCode;
import com.ssafy.miro.common.exception.GlobalException;
import com.ssafy.miro.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AttractionService {
    private final AttractionRepository attractionRepository;
    private final AttractionLikeRepository attractionLikeRepository;

    public Page<AttractionListItem> selectAllAttractions(Pageable pageable, AttractionSearchFilter filter) {
        Page<AttractionListItem> result;

        if (filter.sido() == null) throw new GlobalException(ErrorCode.INVALID_REQUEST);
        else if (filter.guguns() == null &&
                filter.attractionType() == null &&
                filter.keyword() == null) {
            result = attractionRepository.findAllBySidoCode(filter.sido(), pageable).map(AttractionListItem::of);
        } else if (filter.guguns() == null && filter.attractionType() == null) {
            result = attractionRepository.findAllBySidoCodeAndTitleContaining(filter.sido(), filter.keyword(), pageable).map(AttractionListItem::of);
        } else if (filter.guguns() == null && filter.keyword() == null) {
            result = attractionRepository.findAllBySidoCodeAndContentTypeIdIn(filter.sido(), filter.attractionType(), pageable).map(AttractionListItem::of);
        } else if (filter.keyword() == null && filter.attractionType() == null) {
            result = attractionRepository.findAllBySidoCodeAndGuGunCodeIn(filter.sido(), filter.guguns(), pageable).map(AttractionListItem::of);
        } else if (filter.guguns() == null) {
            result = attractionRepository.findAllBySidoCodeAndContentTypeIdInAndTitleContaining(filter.sido(), filter.attractionType(), filter.keyword(), pageable).map(AttractionListItem::of);
        } else if (filter.keyword() == null) {
            result = attractionRepository.findAllBySidoCodeAndGuGunCodeInAndContentTypeIdIn(filter.sido(), filter.guguns(), filter.attractionType(), pageable).map(AttractionListItem::of);
        } else if (filter.attractionType() == null) {
            result = attractionRepository.findAllBySidoCodeAndGuGunCodeInAndTitleContaining(filter.sido(), filter.guguns(), filter.keyword(), pageable).map(AttractionListItem::of);
        } else {
            result = attractionRepository.findAllBySidoCodeAndGuGunCodeInAndContentTypeIdInAndTitleContaining(
                    filter.sido(), filter.guguns(), filter.attractionType(), filter.keyword(), pageable
            ).map(AttractionListItem::of);
        }

        return result;
    }

    public AttractionLikeItem likeHandler(User user, Integer attractionNo) {
        if (attractionLikeRepository.existsByUserIdAndAttractionNo(user.getId(), attractionNo)) {
            attractionLikeRepository.deleteByUserIdAndAttractionNo(user.getId(), attractionNo);
            return AttractionLikeItem.of(attractionLikeRepository.countByAttractionNo(attractionNo), false);
        }

        Attraction attraction = attractionRepository.findById(attractionNo).orElseThrow(() -> new AttractionNotFoundException(ErrorCode.NOT_FOUND_ATTRACTION_ID));
        attractionLikeRepository.save(new AttractionLike(attraction, user));
        return AttractionLikeItem.of(attractionLikeRepository.countByAttractionNo(attractionNo), true);
    }

    public AttractionDetailItem getAttractionDetail(User user, Integer attractionNo) {
        Attraction attraction = attractionRepository.findById(attractionNo).orElseThrow(() -> new AttractionNotFoundException(ErrorCode.NOT_FOUND_ATTRACTION_ID));
        AttractionLikeItem attractionLikeItem = getAttractionLikeInfo(user, attractionNo);

        attraction.increaseViewCount();
        return AttractionDetailItem.of(attraction, attractionLikeItem);
    }

    private AttractionLikeItem getAttractionLikeInfo(User user, Integer attractionNo) {
        boolean attractionLike = false;
        if (user != null) attractionLike = attractionLikeRepository.existsByUserIdAndAttractionNo(user.getId(), attractionNo);
        return new AttractionLikeItem(
                attractionLikeRepository.countByAttractionNo(attractionNo),
                attractionLike);
    }

    private Attraction selectAttractionByAttractionNo(Integer attractionNo) {
        return attractionRepository.findById(attractionNo)
                .orElseThrow(() -> new AttractionNotFoundException(ErrorCode.NOT_FOUND_ATTRACTION_ID));
    }
}
