package com.ssafy.miro.common.jwt;

import com.ssafy.miro.common.exception.GlobalException;
import org.springframework.stereotype.Component;

import static com.ssafy.miro.common.code.ErrorCode.INVALID_ACCESS_TOKEN;

@Component
public class BearerAuthorizationExtractor {
    private static final String BEARER_TYPE = "Bearer ";

    public String extractAccessToken(String header) {
        if (header != null && header.startsWith(BEARER_TYPE)) {
            return header.substring(BEARER_TYPE.length()).trim();
        }
        throw new GlobalException(INVALID_ACCESS_TOKEN);
    }
}
