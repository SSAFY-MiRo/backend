package com.ssafy.miro.attraction.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "contenttypes")
@Getter
@NoArgsConstructor
public class ContentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_type_id")
    private Integer id;
    @Column(name = "content_type_name", nullable = false, length = 50)
    private String name;
}
