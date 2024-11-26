package com.ssafy.miro.attraction.domain.repository;

import com.ssafy.miro.attraction.domain.Attraction;
import com.ssafy.miro.attraction.domain.Sido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttractionRepository extends JpaRepository<Attraction, Integer>, AttractionRepositoryCustom {
    public List<Attraction> findAllBySido(Sido sido);
}
