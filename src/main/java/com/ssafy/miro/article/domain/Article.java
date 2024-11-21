package com.ssafy.miro.article.domain;

import com.ssafy.miro.common.auditing.BaseEntity;
import com.ssafy.miro.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity(name = "articles")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Article extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private ArticleCategory category;
    @Column(nullable = false, length = 90)
    private String title;
    @Column(nullable = false)
    private String content;
    @ColumnDefault("0")
    private Long view=0L;

    public Article(User user, String title, String content, ArticleCategory category) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.category = category;
        this.view = 0L;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void updateDelete() {
        this.updateDeleted();
    }
}
