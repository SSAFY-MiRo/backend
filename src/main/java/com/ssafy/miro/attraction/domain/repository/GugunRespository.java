package com.ssafy.miro.attraction.domain.repository;

import com.ssafy.miro.attraction.domain.Gugun;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GugunRespository extends JpaRepository<Gugun, Integer> {
    List<Gugun> findAllBySidoCode(Integer sidoCode);
}
