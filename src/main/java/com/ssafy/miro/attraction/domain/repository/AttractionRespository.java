package com.ssafy.miro.attraction.domain.repository;

import com.ssafy.miro.attraction.domain.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttractionRespository extends JpaRepository<Attraction, Integer> {
}
