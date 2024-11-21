package com.ssafy.miro.attraction.domain.repository;

import com.ssafy.miro.attraction.domain.AttractionLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttractionLikeRepository extends JpaRepository<AttractionLike, Long> {
    void deleteByUserIdAndAttractionNo(Long userId, Integer attractionNo);
    Long countByAttractionNo(Integer attractionNo);
    boolean existsByUserIdAndAttractionNo(Long userId, Integer attractionNo);
}
