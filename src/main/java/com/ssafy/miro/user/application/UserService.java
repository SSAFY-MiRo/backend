package com.ssafy.miro.user.application;

import com.ssafy.miro.user.application.response.UserInfo;
import com.ssafy.miro.user.domain.User;
import com.ssafy.miro.user.domain.repository.UserRepository;
import com.ssafy.miro.user.exception.EmailDuplicateException;
import com.ssafy.miro.user.exception.NonValidationPasswordException;
import com.ssafy.miro.user.exception.UserNotFoundException;
import com.ssafy.miro.user.presentation.request.UserCreateRequest;
import com.ssafy.miro.user.presentation.request.UserLoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.ssafy.miro.common.code.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void createUser(UserCreateRequest userCreateRequest) {
        findByEmail(userCreateRequest.email()).ifPresent(user->{throw new EmailDuplicateException(EMAIL_DUPLICATED);});
        userRepository.save(userCreateRequest.toUser());
    }

    public UserInfo loginUser(UserLoginRequest userLoginRequest) {
        User user=findUserByEmail(userLoginRequest.email());
        return UserInfo.of(user);
    }

    public void validatePassword(String email, String password) {
        User user=findUserByEmail(email);
        if(!user.getPassword().equals(password)) {
            throw new NonValidationPasswordException(NON_VALIDATED_PASSWORD);
        }
    }
    private Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    private User findUserByEmail(String email) {
        return findByEmail(email).orElseThrow(()->new UserNotFoundException(NOT_FOUND_USER_EMAIL));
    }
}
