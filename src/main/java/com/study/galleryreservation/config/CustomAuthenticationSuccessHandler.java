package com.study.galleryreservation.config;

import com.study.galleryreservation.domain.member.Member;
import com.study.galleryreservation.domain.session.SessionUser;
import com.study.galleryreservation.repository.MemberRepository;
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

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        Member member;

        // 1. 소셜 로그인(OAuth2User)인 경우 처리
        if (authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

            // UserService(saveOrUpdate)에서 "kakao_번호" 형식으로 username을 저장했으므로 똑같이 조합해줍니다.
            // 만약 UserService에서 nameKey를 다르게 썼다면 이 부분이 핵심입니다.
            String registrationId = "kakao"; // 우선 카카오 기준으로 작성 (필요시 분기 처리)
            String providerId = oAuth2User.getName(); // 카카오 고유 ID 값
            String username = registrationId + "_" + providerId;

            member = memberRepository.findByUsername(username)
                    .orElseThrow(() -> new IllegalArgumentException("소셜 사용자를 찾을 수 없습니다: " + username));
        }
        // 2. 일반 로그인(Form Login)인 경우 처리
        else {
            String username = authentication.getName();
            member = memberRepository.findByUsername(username)
                    .orElseThrow(() -> new IllegalArgumentException("일반 사용자를 찾을 수 없습니다: " + username));
        }

        // 3. 세션에 유저 정보 저장 (메인 화면에서 인식할 수 있게)
        HttpSession session = request.getSession();
        session.setAttribute("user", new SessionUser(member));

        // 4. 메인으로 이동
        response.sendRedirect("/");
    }
}