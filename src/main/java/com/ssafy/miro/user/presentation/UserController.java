package com.ssafy.miro.user.presentation;

import com.ssafy.miro.common.ApiResponse;
import com.ssafy.miro.user.application.UserService;
import com.ssafy.miro.user.application.response.UserInfo;
import com.ssafy.miro.user.presentation.request.UserCheckPwdRequest;
import com.ssafy.miro.user.presentation.request.UserCreateRequest;
import com.ssafy.miro.user.presentation.request.UserLoginRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static com.ssafy.miro.common.code.SuccessCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> createUser(@RequestBody @Valid UserCreateRequest userCreateRequest) {
        userService.createUser(false, userCreateRequest);
        return ResponseEntity.created(URI.create("/user")).body(ApiResponse.of(CREATE_USER, null));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserInfo>> login(@RequestBody @Valid UserLoginRequest userLoginRequest) {
        UserInfo userInfo = userService.loginUser(userLoginRequest);
        return ResponseEntity.ok().body(ApiResponse.onSuccess(userInfo));
    }

    @PostMapping("/verify-password")
    public ResponseEntity<ApiResponse<Object>> checkPassword(@RequestBody @Valid UserCheckPwdRequest userCheckPwdRequest) {
        userService.validatePassword("asdf", userCheckPwdRequest.password());
        return ResponseEntity.ok().body(ApiResponse.onSuccess(null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Object>> getUserInfo() {
        UserInfo userInfo = userService.getUserInfo(1L);
        return ResponseEntity.ok().body(ApiResponse.onSuccess(userInfo));
    }
}
