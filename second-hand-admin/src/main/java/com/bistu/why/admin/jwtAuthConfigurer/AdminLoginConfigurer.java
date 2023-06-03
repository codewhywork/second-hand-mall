package com.bistu.why.admin.jwtAuthConfigurer;

import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;



/**
 * @author why
 */ //<B extends HttpSecurityBuilder<B>,
// T extends AbstractAuthenticationFilterConfigurer<B, T, F>,
// F extends AbstractAuthenticationProcessingFilter>
public class AdminLoginConfigurer<H extends HttpSecurityBuilder<H>> extends AbstractAuthenticationFilterConfigurer<H, AdminLoginConfigurer<H>, AdminAuthenticationFilter> {

    public AdminLoginConfigurer(String defaultLoginProcessingUrl) {
        super(new AdminAuthenticationFilter(defaultLoginProcessingUrl), defaultLoginProcessingUrl);
    }


    @Override
    protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
        return new AntPathRequestMatcher(loginProcessingUrl, "POST");
    }
}
