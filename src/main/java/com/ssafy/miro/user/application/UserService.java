package com.ssafy.miro.user.application;

import com.ssafy.miro.user.domain.repository.UserRepository;
import com.ssafy.miro.user.presentation.request.UserCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void createUser(UserCreateRequest userCreateRequest) {
        userRepository.save(userCreateRequest.toUser());
    }
}
