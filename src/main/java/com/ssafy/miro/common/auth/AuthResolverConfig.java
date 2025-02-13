package com.ssafy.miro.common.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AuthResolverConfig implements WebMvcConfigurer {
    private final AuthArgumentResolver authArgumentResolver;
    private final NotEssentialArgumentResolver notEssentialArgumentResolver;

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authArgumentResolver);
        resolvers.add(notEssentialArgumentResolver);
    }
}
