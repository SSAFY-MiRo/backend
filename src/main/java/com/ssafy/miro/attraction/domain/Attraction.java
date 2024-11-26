package com.ssafy.miro.attraction.domain;

import jakarta.persistence.*;
import lombok.Cleanup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.math.BigDecimal;

@Entity(name = "attractions")
@Getter
@NoArgsConstructor
public class Attraction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer no;
    private Integer contentId;
    @Column(length = 100)
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_type_id")
    private ContentType contentType;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "area_code", referencedColumnName = "sido_code")
    private Sido sido;
    @ColumnDefault("0")
    private Long view=0L;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "si_gun_gu_code", referencedColumnName = "si_gun_gu_code")
    private Gugun guGunCode;
    @Column(length = 100)
    private String firstImage1;
    @Column(length = 100)
    private String firstImage2;
    private Integer mapLevel;
    @Column(precision = 20, scale = 17, nullable = false)
    private BigDecimal latitude;
    @Column(precision = 20, scale = 17, nullable = false)
    private BigDecimal longitude;
    @Column(length = 20)
    private String tel;
    @Column(length = 100)
    private String addr1;
    @Column(length = 100)
    private String addr2;
    @Column(length = 500)
    private String homepage;
    private String overview;

    public void increaseViewCount() {
        this.view++;
    }
}
