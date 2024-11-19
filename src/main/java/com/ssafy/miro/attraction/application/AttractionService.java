package com.ssafy.miro.attraction.application;

import com.ssafy.miro.attraction.application.response.AttractionItem;
import com.ssafy.miro.attraction.application.response.AttractionLikeItem;
import com.ssafy.miro.attraction.domain.Attraction;
import com.ssafy.miro.attraction.domain.AttractionLike;
import com.ssafy.miro.attraction.domain.repository.AttractionLikeRepository;
import com.ssafy.miro.attraction.domain.repository.AttractionRepository;
import com.ssafy.miro.attraction.exception.AttractionNotFoundException;
import com.ssafy.miro.common.code.ErrorCode;
import com.ssafy.miro.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AttractionService {
    private final AttractionRepository attractionRepository;
    private final AttractionLikeRepository attractionLikeRepository;

    @Transactional(readOnly = true)
    public List<AttractionItem> selectAllAttraction(Pageable pageable) {
        return attractionRepository.findAll(pageable).stream().map(AttractionItem::of).toList();
    }

    @Transactional(readOnly = true)
    public AttractionItem selectAttractionByNo(Integer id) {
        return attractionRepository.findById(id).map(AttractionItem::of).orElseThrow(() -> new AttractionNotFoundException(ErrorCode.NOT_FOUND_COMMENT_ID));
    }

    public AttractionLikeItem likeAttraction(Integer no, User user) {
//        attractionLikeRepository.save();
        return AttractionLikeItem.of(attractionLikeRepository.countByAttractionNo(no), true);
    }

    public AttractionLikeItem unlikeAttraction(Integer no, User user) {
        attractionLikeRepository.deleteByUserIdAndAttractionNo(user.getId(), no);
        return AttractionLikeItem.of(attractionLikeRepository.countByAttractionNo(no), false);
    }
}
