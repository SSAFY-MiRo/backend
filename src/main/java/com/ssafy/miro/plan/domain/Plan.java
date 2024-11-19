package com.ssafy.miro.plan.domain;

import com.ssafy.miro.common.auditing.BaseEntity;
import com.ssafy.miro.plan.presentation.request.PlanInfo;
import com.ssafy.miro.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
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
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;
    @Column(nullable = false, length = 100)
    private String title;
    @Column(nullable = false, length = 30)
    private String location;
    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private PlanAccessLevel accessLevel;
    private Date startDate;
    private Date endDate;


    public Plan(String title, String location, PlanAccessLevel planAccessLevel, Date startDate, Date endDate) {
        this.title = title;
        this.location = location;
        this.accessLevel = planAccessLevel;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Plan of(PlanInfo planInfo) {
        return new Plan(
                planInfo.title(),
                planInfo.location(),
                PlanAccessLevel.PRIVATE,
                planInfo.startDate(),
                planInfo.endDate()
        );
    }
}
