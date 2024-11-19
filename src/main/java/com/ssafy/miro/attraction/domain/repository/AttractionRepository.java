package com.ssafy.miro.attraction.domain.repository;

import com.ssafy.miro.attraction.domain.Attraction;
import com.ssafy.miro.attraction.domain.AttractionLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttractionRepository extends JpaRepository<Attraction, Integer> {
}
