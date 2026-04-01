package com.study.galleryreservation.config;

import com.study.galleryreservation.service.CustomOAuth2UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomAuthenticationSuccessHandler customSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((CsrfConfigurer<HttpSecurity> csrf) -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()));

        http.authorizeHttpRequests(authz -> authz
                .requestMatchers("/css/**", "/js/**").permitAll()
                .requestMatchers("/", "/member/join", "/member/login").permitAll()
                .requestMatchers("/gallery/list", "/gallery/detail/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
        );

        http.formLogin(login -> login
                .loginPage("/member/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/member/loginAction")
                .successHandler(customSuccessHandler)
                .failureUrl("/member/login?error=true")
        );

        http.logout(logout -> logout
                .logoutUrl("/member/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
        );

        http.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class);

        http.oauth2Login(oauth -> oauth
                .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                .successHandler(oauthSuccessHandler())
                .failureHandler(oauthFailureHandler())
        );

        return http.build();
    }

    // CSRF 토큰을 Thymeleaf 렌더링 전에 강제로 로드하는 필터.
    // Spring Security 6의 lazy loading 방식으로 인해 index.html처럼 HTML이 긴 경우
    // CSS 렌더링 도중 응답 버퍼가 flush되어 Set-Cookie 헤더가 누락될 수 있음.
    // 이 필터가 렌더링 시작 전에 csrfToken.getToken()을 호출하여
    // CSRF 쿠키를 응답 헤더에 미리 세팅함으로써 로그인 직후 403 에러를 방지함.
    private static final class CsrfCookieFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {
            CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
            if (csrfToken != null) {
                // getToken() 호출로 지연 로드(lazy load)를 강제 실행 → Set-Cookie 헤더 즉시 세팅
                csrfToken.getToken();
            }
            filterChain.doFilter(request, response);
        }
    }

    @Bean
    public SimpleUrlAuthenticationSuccessHandler oauthSuccessHandler() {
        return new SimpleUrlAuthenticationSuccessHandler("/");
    }

    @Bean
    public SimpleUrlAuthenticationFailureHandler oauthFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler("/member/login?error=true");
    }
}