package com.ssafy.miro.common.auth;

import com.ssafy.miro.common.code.ErrorCode;
import com.ssafy.miro.common.exception.GlobalException;
import com.ssafy.miro.common.jwt.BearerAuthorizationExtractor;
import com.ssafy.miro.common.jwt.JwtProvider;
import com.ssafy.miro.user.domain.User;
import com.ssafy.miro.user.domain.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Arrays;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OAuthArgumentResolver implements HandlerMethodArgumentResolver {
    private static final String REFRESH_TOKEN = "refresh-token";
    private final JwtProvider jwtProvider;
    private final BearerAuthorizationExtractor bearerAuthorizationExtractor;
    private final UserRepository userRepository;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.withContainingClass(User.class)
                .hasParameterAnnotation(Auth.class);
    }

    @Override
    public User resolveArgument(MethodParameter parameter,
                                ModelAndViewContainer mavContainer,
                                NativeWebRequest webRequest,
                                WebDataBinderFactory binderFactory
    ) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if (request == null) throw new Exception();

        try {
            String refreshToken = extractRefreshToken(request.getCookies());
            String accessToken = bearerAuthorizationExtractor.extractAccessToken(webRequest.getHeader(HttpHeaders.AUTHORIZATION));

            jwtProvider.validateTokens(accessToken, refreshToken);

            Long id = jwtProvider.getId(accessToken);

            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()) return user.get();
            else if (parameter.hasParameterAnnotation(NonEssential.class)) return null;

            throw new GlobalException(ErrorCode.INVALID_USER_LOGGED_IN);
        } catch (Exception e) {
            if (parameter.hasParameterAnnotation(NonEssential.class)) throw new GlobalException(ErrorCode.INVALID_USER_LOGGED_IN);
            return null;
        }
    }

    private String extractRefreshToken(Cookie... cookies) throws Exception {
        if (cookies == null) throw new Exception();
        return Arrays.stream(cookies)
                .filter(this::isValidRefreshToken)
                .findFirst()
                .orElseThrow(Exception::new)
                .getValue();
    }


    private boolean isValidRefreshToken(Cookie cookie) {
        return cookie.getName().equals(REFRESH_TOKEN);
    }
}
