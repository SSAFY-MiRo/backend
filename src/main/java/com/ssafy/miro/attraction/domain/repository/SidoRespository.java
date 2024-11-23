package com.ssafy.miro.attraction.domain.repository;

import com.ssafy.miro.attraction.domain.Sido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SidoRespository extends JpaRepository<Sido, Long> {
    Optional<Sido> findByCode(Integer code);
}
