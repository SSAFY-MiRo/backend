package com.ssafy.miro.attraction.domain.repository;

import com.ssafy.miro.attraction.domain.Attraction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttractionRespository extends JpaRepository<Attraction, Integer> {
    Page<Attraction> findAllBySidoCode(Integer sidoCode,
                                       Pageable pageable);

    Page<Attraction> findAllBySidoCodeAndTitleContaining(Integer sidoCode,
                                                         String title,
                                                         Pageable pageable);

    Page<Attraction> findAllBySidoCodeAndGuGunCodeIn(Integer sidoCode,
                                                     List<Integer> guGunCode,
                                                     Pageable pageable);

    Page<Attraction> findAllBySidoCodeAndContentTypeIn(Integer sidoCode,
                                                     List<Integer> contentType,
                                                     Pageable pageable);

    Page<Attraction> findAllBySidoCodeAndContentTypeInAndTitleContaining(Integer sidoCode,
                                                                         List<Integer> guGunCode,
                                                                         String title,
                                                                         Pageable pageable);

    Page<Attraction> findAllBySidoCodeAndGugunCodeInAndTitleContaining(Integer sidoCode,
                                                                       List<Integer> guGunCode,
                                                                       String title,
                                                                       Pageable pageable);

    Page<Attraction> findAllBySidoCodeAndGuGunCodeInAndContentTypeIn(Integer sidoCode,
                                                        List<Integer> guGunCode,
                                                        List<Integer> contentType,
                                                        Pageable pageable);

    Page<Attraction> findAllBySidoCodeAndGuGunCodeInAndContentTypeInAndTitleContaining(Integer sidoCode,
                                                                                     List<Integer> guGunCode,
                                                                                     List<Integer> contentType,
                                                                                       String title,
                                                                                     Pageable pageable);
}
