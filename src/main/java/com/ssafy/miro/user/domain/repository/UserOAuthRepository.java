package com.ssafy.miro.user.domain.repository;

import com.ssafy.miro.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOAuthRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
