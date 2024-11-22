package com.ssafy.miro.user.domain;

import com.ssafy.miro.common.auditing.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.mindrot.jbcrypt.BCrypt;

@Getter
@Entity(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @Column(length = 100)
//    private String authId;
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
    @ColumnDefault("false")
    @Column(name = "is_oauth_authenticated")
    private Boolean isOAuth = false;

    public User(String email, String password, String nickname, UserType userType, String profileImage, Boolean isOAuth) {
        this.email = email;
        this.password = encryptPassword(password);
        this.nickname = nickname;
        this.userType = userType;
        this.profileImage = profileImage;
        this.isOAuth = isOAuth;
    }

    public void updateUserWithImage(String nickname,String password, String profileImage) {
        this.nickname = nickname;
        this.password = encryptPassword(password);
        this.profileImage = profileImage;
    }

    public void updateUser(String nickname,String password) {
        this.nickname = nickname;
        this.password = encryptPassword(password);
    }

    // 비밀번호 암호화
    public static String encryptPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // 비밀번호 검증
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
