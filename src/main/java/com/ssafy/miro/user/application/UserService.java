package com.ssafy.miro.user.application;

import com.ssafy.miro.image.application.ImageService;
import com.ssafy.miro.user.application.response.UserInfo;
import com.ssafy.miro.user.domain.User;
import com.ssafy.miro.user.domain.repository.UserRepository;
import com.ssafy.miro.user.exception.EmailDuplicateException;
import com.ssafy.miro.user.exception.NonValidationPasswordException;
import com.ssafy.miro.user.exception.UserNotFoundException;
import com.ssafy.miro.user.presentation.request.UserCreateRequest;
import com.ssafy.miro.user.presentation.request.UserLoginRequest;
import com.ssafy.miro.user.presentation.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static com.ssafy.miro.common.code.ErrorCode.*;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ImageService imageService;

    @Transactional
    public Long createUser(boolean isOAuth, UserCreateRequest userCreateRequest) {
        findByEmail(userCreateRequest.email()).ifPresent(user->{throw new EmailDuplicateException(EMAIL_DUPLICATED);});
        return userRepository.save(userCreateRequest.toUser(isOAuth)).getId();
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

    @Transactional
    public void updateUser(User user, MultipartFile file, UserUpdateRequest userUpdateRequest) throws IOException {
        System.out.println(userUpdateRequest.toString());
        if(!userUpdateRequest.password().equals(userUpdateRequest.passwordConfirm())) {
            throw new NonValidationPasswordException(NON_VALIDATED_PASSWORD);
        }
        if(file != null) {
            // 파일 저장 로직 호출
            String filePath = imageService.saveFile(file);
            user.updateUserWithImage(userUpdateRequest.nickname(), userUpdateRequest.password(), filePath);
        } else {
            user.updateUser(userUpdateRequest.nickname(), userUpdateRequest.password());
        }
    }

    public UserInfo getUserInfo(Long id) {
        User user=findById(id);
        return UserInfo.of(user);
    }

    private User findById(Long id){
        return userRepository.findById(id).orElseThrow(()->new UserNotFoundException(NOT_FOUND_USER_ID));
    }


    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    private User findUserByEmail(String email) {
        return findByEmail(email).orElseThrow(()->new UserNotFoundException(NOT_FOUND_USER_EMAIL));
    }
}
