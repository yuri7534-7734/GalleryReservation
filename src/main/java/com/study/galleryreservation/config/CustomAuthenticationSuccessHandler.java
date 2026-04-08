package com.study.galleryreservation.config;

import com.study.galleryreservation.domain.member.Member;
import com.study.galleryreservation.domain.session.SessionUser;
import com.study.galleryreservation.domain.session.SnsUser;
import com.study.galleryreservation.domain.session.UserRole;
import com.study.galleryreservation.repository.MemberRepository;
import com.study.galleryreservation.repository.SnsUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final MemberRepository memberRepository;
    private final SnsUserRepository snsUserRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        HttpSession session = request.getSession();

        if (authentication.getPrincipal() instanceof OAuth2User oAuth2User) {
            // OAuth2 로그인 — attributes에서 provider+providerId 꺼내서 SnsUser 조회 후 세션 저장
            String username = (String) oAuth2User.getAttributes().get("username"); // "kakao_123", "google_abc"
            String[] parts = username.split("_", 2);
            String provider = parts[0];
            String providerId = parts[1];

            SnsUser snsUser = snsUserRepository.findByProviderAndProviderId(provider, providerId)
                    .orElseThrow(() -> new IllegalArgumentException("SNS 사용자를 찾을 수 없습니다."));

            session.setAttribute("user", new SessionUser(snsUser));

        } else {
            // 일반 폼 로그인
            String username = authentication.getName();
            Member member = memberRepository.findByUsername(username)
                    .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

            session.setAttribute("user", new SessionUser(member));
        }

        response.sendRedirect("/");
    }
}