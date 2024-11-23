package com.ssafy.miro.article.domain;

import com.ssafy.miro.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "article_likes")
@NoArgsConstructor
@Getter
public class ArticleLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    public ArticleLike(final User user, final Article article) {
        this.user = user;
        this.article = article;
    }
}
