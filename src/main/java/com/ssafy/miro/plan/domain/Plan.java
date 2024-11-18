package com.ssafy.miro.plan.domain;

import com.ssafy.miro.common.auditing.BaseEntity;
import com.ssafy.miro.member.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity(name = "plans")
@NoArgsConstructor
@Getter
public class Plan extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(nullable = false, length = 100)
    private String title;
    @Column(nullable = false, length = 30)
    private String location;
    @Column(nullable = false, length = 10)
    private String accessLevel;
    Date startDate;
    Date endDate;
}
