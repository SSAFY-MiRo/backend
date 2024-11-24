package com.ssafy.miro.user.presentation;

import com.ssafy.miro.auth.domain.dto.UserToken;
import com.ssafy.miro.common.ApiResponse;
import com.ssafy.miro.common.auth.Auth;
import com.ssafy.miro.image.application.ImageService;
import com.ssafy.miro.user.application.UserService;
import com.ssafy.miro.user.application.response.UserInfo;
import com.ssafy.miro.user.domain.User;
import com.ssafy.miro.user.domain.UserType;
import com.ssafy.miro.user.presentation.request.UserCheckPwdRequest;
import com.ssafy.miro.user.presentation.request.UserCreateRequest;
import com.ssafy.miro.user.presentation.request.UserLoginRequest;
import com.ssafy.miro.user.presentation.request.UserUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;

import static com.ssafy.miro.common.code.ErrorCode.*;
import static com.ssafy.miro.common.code.SuccessCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> createUser(@RequestBody @Valid UserCreateRequest userCreateRequest) {
        userService.createUser(false, userCreateRequest, UserType.INVALID);
        return ResponseEntity.created(URI.create("/user")).body(ApiResponse.of(CREATE_USER, null));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserToken>> login(@RequestBody @Valid UserLoginRequest userLoginRequest) {
        UserToken userInfo = userService.loginUser(userLoginRequest);
        return ResponseEntity.ok().body(ApiResponse.onSuccess(userInfo));
    }

    @PostMapping("/verify-password")
    public ResponseEntity<ApiResponse<Object>> checkPassword(@Auth User user, @RequestBody @Valid UserCheckPwdRequest userCheckPwdRequest) {
        userService.validatePassword(user, userCheckPwdRequest.password());
        return ResponseEntity.ok().body(ApiResponse.onSuccess(null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Object>> getUserInfo(@Auth User user) {
        System.out.println(user);
        UserInfo userInfo = userService.getUserInfo(user);
        return ResponseEntity.ok().body(ApiResponse.onSuccess(userInfo));
    }

    @PutMapping(consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<Object>> updateUser(@Auth User user, @RequestParam(value = "file", required = false) MultipartFile file, @ModelAttribute @Valid UserUpdateRequest userUpdateRequest) throws IOException {
        userService.updateUser(user, file, userUpdateRequest);
        return ResponseEntity.ok().body(ApiResponse.onSuccess(null));
    }

}
