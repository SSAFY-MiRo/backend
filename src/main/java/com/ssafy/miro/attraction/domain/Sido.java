package com.ssafy.miro.attraction.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Cleanup;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "sidos")
@NoArgsConstructor
@Getter
public class Sido {
    @Id
    @GeneratedValue
    private Long no;
    @Column(name = "sido_code")
    private int code;
    @Column(length = 20)
    private String name;
}
