package com.ssafy.miro.user.presentation;

import com.ssafy.miro.common.ApiResponse;
import com.ssafy.miro.user.application.UserService;
import com.ssafy.miro.user.application.response.UserInfo;
import com.ssafy.miro.user.presentation.request.UserCreateRequest;
import com.ssafy.miro.user.presentation.request.UserLoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> createUser(@RequestBody UserCreateRequest userCreateRequest) {
        userService.createUser(userCreateRequest);
        return ResponseEntity.created(URI.create("/user")).body(null);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserInfo>> login(@RequestBody UserLoginRequest userLoginRequest) {
        UserInfo userInfo = userService.loginUser(userLoginRequest);
        return ResponseEntity.ok().body(ApiResponse.onSuccess(userInfo));
    }
}
