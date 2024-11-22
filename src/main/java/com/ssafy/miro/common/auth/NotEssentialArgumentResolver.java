package com.ssafy.miro.common.auth;

import com.ssafy.miro.common.code.ErrorCode;
import com.ssafy.miro.common.exception.GlobalException;
import com.ssafy.miro.common.jwt.JwtProvider;
import com.ssafy.miro.user.domain.User;
import com.ssafy.miro.user.domain.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class NotEssentialArgumentResolver implements HandlerMethodArgumentResolver {
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.withContainingClass(User.class)
                .hasParameterAnnotation(NonEssential.class);
    }

    @Override
    public User resolveArgument(MethodParameter parameter,
                                ModelAndViewContainer mavContainer,
                                NativeWebRequest webRequest,
                                WebDataBinderFactory binderFactory
    ) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if (request == null) throw new GlobalException(ErrorCode.INVALID_REQUEST);

        try{
            Long id = jwtProvider.validateAndExtractUserId(request, webRequest);
            Optional<User> user = userRepository.findById(id);
            return user.orElse(null);
        }
        catch (GlobalException e){
            return null;
        }
    }
}
