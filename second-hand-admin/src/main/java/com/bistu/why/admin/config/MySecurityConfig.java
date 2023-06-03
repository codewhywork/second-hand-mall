package com.bistu.why.admin.config;

import com.bistu.why.admin.jwtAuthConfigurer.AdminAuthenticationFilter;
import com.bistu.why.admin.jwtAuthConfigurer.AdminAuthenticationProvider;
import com.bistu.why.admin.jwtAuthConfigurer.AdminLoginConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.context.SecurityContextRepository;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * @author why
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MySecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http,
                                            AuthenticationSuccessHandler successHandler,
                                            AuthenticationFailureHandler failureHandler,
                                            LogoutSuccessHandler logoutSuccessHandler,
                                            AuthenticationEntryPoint authenticationEntryPoint,
                                            SecurityContextRepository securityContextRepository) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/login.html").permitAll()
                .and()
                .formLogin()
                .passwordParameter("passwd")
                .usernameParameter("uname")
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .securityContextRepository(securityContextRepository)
                .loginProcessingUrl("/doLogin")
                .loginPage("/login.html")
                .defaultSuccessUrl("/index.html", true)
                .and()
                .securityContext()
                .requireExplicitSave(true)
                .securityContextRepository(securityContextRepository)
                .and().authorizeRequests().anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(logoutSuccessHandler)
                .and()
                .csrf().disable();

        //利用反射把filter添加进map
        Field filterOrders = HttpSecurity.class.getDeclaredField("filterOrders");
        filterOrders.setAccessible(true);
        Object filterOrderRegistration = filterOrders.get(http);
        Method put = filterOrderRegistration.getClass().getDeclaredMethod("put", Class.class, int.class);
        put.setAccessible(true);
        put.invoke(filterOrderRegistration, AdminAuthenticationFilter.class, 1899);

        AdminLoginConfigurer<HttpSecurity> adminLoginConfigurer = new AdminLoginConfigurer<>("/doLogin");
        adminLoginConfigurer
                .successHandler(successHandler)
                .failureHandler(failureHandler);
        http.apply(adminLoginConfigurer);
        http.authenticationProvider(new AdminAuthenticationProvider());

        return http.build();
    }
}
