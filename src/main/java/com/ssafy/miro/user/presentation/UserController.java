package com.ssafy.miro.user.presentation;

import com.ssafy.miro.common.ApiResponse;
import com.ssafy.miro.user.application.UserService;
import com.ssafy.miro.user.presentation.request.UserCreateRequest;
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
}
