package com.ssafy.miro.common.auditing;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EnableJpaAuditing
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt;
    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean deleted = false;

    public void updateDeleted() {
        this.deleted = true;
    }
}
