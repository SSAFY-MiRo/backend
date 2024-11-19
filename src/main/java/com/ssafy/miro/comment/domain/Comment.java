package com.ssafy.miro.comment.domain;

import com.ssafy.miro.article.domain.Article;
import com.ssafy.miro.common.auditing.BaseEntity;
import com.ssafy.miro.member.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DialectOverride;

@Getter
@NoArgsConstructor
@Entity(name = "comments")
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;
    @Column(nullable = false, length = 300)
    private String content;

    public void updateComment(String content) {
        this.content = content;
    }
}
