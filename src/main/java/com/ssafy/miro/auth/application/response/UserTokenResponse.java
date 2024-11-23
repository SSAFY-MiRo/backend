package com.ssafy.miro.auth.application.response;

import com.ssafy.miro.user.application.response.UserInfo;

public record UserTokenResponse(String accessToken, UserInfo userInfo) {
}
