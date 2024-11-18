package com.ssafy.miro.member.domain;

import com.ssafy.miro.common.auditing.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100)
    private String authId;
    @Column(nullable = false, unique = true, length = 30)
    private String email;
    @Column(nullable = false, length = 100)
    private String password;
    @Column(nullable = false, length = 30)
    private String nickname;
    @Column(nullable = false, length = 10)
    private String userType;
    @Column(nullable = false)
    private String profileImage;
}
