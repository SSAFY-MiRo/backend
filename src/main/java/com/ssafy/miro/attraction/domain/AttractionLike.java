package com.ssafy.miro.attraction.domain;

import com.ssafy.miro.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "attraction_like")
@Getter
@NoArgsConstructor
public class AttractionLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attraction_no")
    private Attraction attraction;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public AttractionLike(Attraction attraction, User user) {
        this.attraction = attraction;
        this.user = user;
    }
}
