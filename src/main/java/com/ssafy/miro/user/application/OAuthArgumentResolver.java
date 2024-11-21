package com.ssafy.miro.user.application;

import com.ssafy.miro.common.code.ErrorCode;
import com.ssafy.miro.common.jwt.BearerAuthorizationExtractor;
import com.ssafy.miro.common.jwt.JwtProvider;
import com.ssafy.miro.user.domain.User;
import com.ssafy.miro.user.domain.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Arrays;

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

            System.out.println(accessToken + " " + refreshToken);
            jwtProvider.validateTokens(accessToken, refreshToken);

            Long id = jwtProvider.getId(accessToken);

            return userRepository.findById(id).orElse(null);

        } catch (Exception e) {
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
        System.out.println(cookie.getName() + " " + cookie.getValue());
        return cookie.getName().equals(REFRESH_TOKEN);
    }
}
