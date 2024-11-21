package com.ssafy.miro.user.domain.repository;

import com.ssafy.miro.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserOAuthRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
