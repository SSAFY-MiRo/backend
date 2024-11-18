package com.ssafy.miro.notification.domain;

import com.ssafy.miro.article.domain.Article;
import com.ssafy.miro.common.auditing.BaseEntity;
import com.ssafy.miro.member.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@NoArgsConstructor
@Entity(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receive_user_id")
    private User receiver;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "send_user_id")
    private User sender;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;
    @ColumnDefault("false")
    private Boolean isRead=false;
    @Column(length = 100)
    private String type;
}
