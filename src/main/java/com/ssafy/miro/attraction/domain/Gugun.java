package com.ssafy.miro.attraction.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "guguns")
@NoArgsConstructor
@Getter
public class Gugun {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;
    @OneToOne
    @JoinColumn(name = "sido_code", referencedColumnName = "sido_code")
    private Sido sido;
    @Column(name = "si_gun_gu_code")
    private String gugunCode;
    @Column(length = 20)
    private String gugunName;
}
