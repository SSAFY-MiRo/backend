package com.ssafy.miro.board.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 90)
    private String title;
    @Column(nullable = false)
    private String content;
    private Long view;

    public Board(String title, String content) {
        this.title = title;
        this.content = content;
        this.view = 0L;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
