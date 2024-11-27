package com.ssafy.miro.attraction.domain.repository;

import com.ssafy.miro.attraction.domain.AttractionLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttractionLikeRepository extends JpaRepository<AttractionLike, Long> {
    @Modifying
    @Query("DELETE FROM attraction_like al WHERE al.user.id = :userId AND al.attraction.id = :attractionNo")
    void removeByUserIdAndAttractionNo(@Param("userId") Long userId, @Param("attractionNo") Integer attractionNo);
    Long countByAttractionNo(Integer attractionNo);
    boolean existsByUserIdAndAttractionNo(Long userId, Integer attractionNo);
}
