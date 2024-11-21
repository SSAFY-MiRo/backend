package com.ssafy.miro.attraction.domain.repository;

import com.ssafy.miro.attraction.domain.Attraction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttractionRepository extends JpaRepository<Attraction, Integer> {
    Page<Attraction> findAllBySidoCode(Integer sidoCode,
                                       Pageable pageable);

    Page<Attraction> findAllBySidoCodeAndTitleContaining(Integer sidoCode,
                                                         String title,
                                                         Pageable pageable);

    Page<Attraction> findAllBySidoCodeAndGuGunCodeIn(Integer sidoCode,
                                                     List<Integer> guGunCode,
                                                     Pageable pageable);

    Page<Attraction> findAllBySidoCodeAndContentTypeIdIn(Integer sidoCode,
                                                     List<Integer> contentType,
                                                     Pageable pageable);

    Page<Attraction> findAllBySidoCodeAndContentTypeIdInAndTitleContaining(Integer sidoCode,
                                                                           List<Integer> guGunCode,
                                                                           String title,
                                                                           Pageable pageable);

    Page<Attraction> findAllBySidoCodeAndGuGunCodeInAndTitleContaining(Integer sidoCode,
                                                                       List<Integer> guGunCode,
                                                                       String title,
                                                                       Pageable pageable);

    Page<Attraction> findAllBySidoCodeAndGuGunCodeInAndContentTypeIdIn(Integer sidoCode,
                                                                       List<Integer> guGunCode,
                                                                       List<Integer> contentType,
                                                                       Pageable pageable);

    Page<Attraction> findAllBySidoCodeAndGuGunCodeInAndContentTypeIdInAndTitleContaining(Integer sidoCode,
                                                                                         List<Integer> guGunCode,
                                                                                         List<Integer> contentType,
                                                                                         String title,
                                                                                         Pageable pageable);
}
