package com.ssafy.miro.user.application;

import com.ssafy.miro.auth.application.response.UserTokenResponse;
import com.ssafy.miro.auth.domain.dto.UserToken;
import com.ssafy.miro.common.ApiResponse;
import com.ssafy.miro.common.exception.GlobalException;
import com.ssafy.miro.common.jwt.JwtProvider;
import com.ssafy.miro.image.application.ImageService;
import com.ssafy.miro.user.application.response.UserInfo;
import com.ssafy.miro.user.domain.User;
import com.ssafy.miro.user.domain.UserType;
import com.ssafy.miro.user.domain.repository.UserRepository;
import com.ssafy.miro.user.presentation.request.UserCreateRequest;
import com.ssafy.miro.user.presentation.request.UserLoginRequest;
import com.ssafy.miro.user.presentation.request.UserUpdateRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.Optional;

import static com.ssafy.miro.common.code.ErrorCode.*;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final JwtProvider  jwtProvider;


    @Transactional
    public Long createUser(boolean isOAuth, UserCreateRequest userCreateRequest, UserType userType) {
        findByEmail(userCreateRequest.email()).ifPresent(user->{throw new GlobalException(EMAIL_DUPLICATED);});
        return userRepository.save(userCreateRequest.toUser(isOAuth, userType)).getId();
    }

    public ResponseEntity<ApiResponse<UserTokenResponse>> loginUser(UserLoginRequest userLoginRequest, HttpServletResponse response) {
        User user=findUserByEmail(userLoginRequest.email());
        if(user.isDeleted()||!user.getUserType().equals(UserType.USER)) {
            throw new GlobalException(NOT_FOUND_USER_ID);
        }
        if(!User.checkPassword(userLoginRequest.password(), user.getPassword())){
            throw new GlobalException(NON_VALIDATED_PASSWORD);
        };

        UserToken userToken=jwtProvider.generateAuthToken(user.getId(), user);

        return jwtProvider.sendToken(response, userToken);
    }

    public void validatePassword(User user, String password) {
        if(!User.checkPassword(password, user.getPassword())) {
            throw new GlobalException(NON_VALIDATED_PASSWORD);
        }
    }

    @Transactional
    public UserInfo updateUser(User user, MultipartFile file, UserUpdateRequest userUpdateRequest) throws IOException {
        System.out.println(userUpdateRequest.toString());
        if(!userUpdateRequest.password().equals(userUpdateRequest.passwordConfirm())) {
            throw new GlobalException(NON_VALIDATED_PASSWORD);
        }
        if(file != null) {
            // 파일 저장 로직 호출
            String filePath = imageService.saveFile(file);
            user.updateUserWithImage(userUpdateRequest.nickname(), userUpdateRequest.password(), filePath);
        } else {
            user.updateUser(userUpdateRequest.nickname(), userUpdateRequest.password());
        }
        return UserInfo.of(user);
    }

    public UserInfo getUserInfo(User user) {
        return UserInfo.of(user);
    }

    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(()->new GlobalException(NOT_FOUND_USER_ID));
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmailAndDeleted(email, false);
    }

    public User findUserByEmail(String email) {
        return findByEmail(email).orElseThrow(()->new GlobalException(NOT_FOUND_USER_EMAIL));
    }


}
