package com.ssafy.miro.user.domain;

import com.ssafy.miro.common.auditing.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100)
    private String authId;
    @Column(nullable = false, unique = true, length = 30)
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Invalid email format")
    private String email;
    @Column(nullable = false, length = 100)
    private String password;
    @Column(nullable = false, length = 30)
    private String nickname;
    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @Column(nullable = false)
    private String profileImage;

    @Builder
    public User(String authId, String email, String password, String nickname, UserType userType, String profileImage) {
        this.authId = authId;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.userType = userType;
        this.profileImage = profileImage;
    }

//    public User(String email, String password, String nickname, String userType, String profileImage) {
//        this.email = email;
//        this.password = password;
//        this.nickname = nickname;
//        this.userType = userType;
//        this.profileImage = profileImage;
//    }
}
