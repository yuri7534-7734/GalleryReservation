package com.study.galleryreservation.service;

import com.study.galleryreservation.config.OAuthAttributes;
import com.study.galleryreservation.domain.member.Member;
import com.study.galleryreservation.domain.member.MemberRole;
import com.study.galleryreservation.domain.session.SessionUser;
import com.study.galleryreservation.domain.session.SnsUser;
import com.study.galleryreservation.domain.session.UserRole;
import com.study.galleryreservation.repository.MemberRepository;
import com.study.galleryreservation.repository.SnsUserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final SnsUserRepository repository;
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attribute = OAuthAttributes.of(
                registrationId,
                userNameAttributeName,
                oAuth2User.getAttributes()
        );

        // 1. 유저 정보 저장 및 업데이트 (SnsUser & Member 테이블 동기화)
        SnsUser user = saveOrUpdate(attribute);

        // [수정] 여기서 직접 HttpSession에 저장하던 로직을 제거했습니다.
        // 대신 SecurityConfig의 SuccessHandler가 이 역할을 수행하게 됩니다.

        String nameKey = attribute.getNameAttributeKey();
        Object nameValue = attribute.getAttributes().get(nameKey);

        if (nameValue == null) {
            throw new OAuth2AuthenticationException(
                    "Missing OAuth2 name attribute. registrationId=" + registrationId + ", key=" + nameKey
            );
        }

        // 2. 권한 정보와 함께 OAuth2User 반환
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(resolveRoleKey(user))),
                attribute.getAttributes(),
                nameKey
        );
    }

    private SnsUser saveOrUpdate(OAuthAttributes authAttributes) {
        String provider = authAttributes.getRegistrationId();
        String providerId = authAttributes.getProviderId();

        String email = resolveEmail(authAttributes);
        String name = resolveName(authAttributes, email);
        String picture = resolvePicture(authAttributes);

        SnsUser snsUser = repository.findByProviderAndProviderId(provider, providerId)
                .map(entity -> entity.update(name, picture, email))
                .orElse(SnsUser.builder()
                        .name(name)
                        .email(email)
                        .picture(picture)
                        .provider(provider)
                        .providerId(providerId)
                        .role(UserRole.ROLE_USER)
                        .build());

        SnsUser savedSnsUser = repository.save(snsUser);

        // Member 테이블 동기화: SNS 로그인을 해도 일반 회원 기능을 사용할 수 있도록 함
        memberRepository.findByEmail(email).orElseGet(() -> memberRepository.save(
                Member.builder()
                        .username(provider + "_" + providerId)   // e.g. kakao_123456
                        .password("SNS_" + UUID.randomUUID())    // 더미 비밀번호
                        .email(email)
                        .role(MemberRole.ROLE_USER)
                        .build()
        ));

        return savedSnsUser;
    }

    private String resolveEmail(OAuthAttributes authAttributes) {
        if (StringUtils.hasText(authAttributes.getEmail())) {
            return authAttributes.getEmail();
        }
        return authAttributes.getRegistrationId() + "_" + authAttributes.getProviderId() + "@no-email.local";
    }

    private String resolveName(OAuthAttributes authAttributes, String email) {
        if (StringUtils.hasText(authAttributes.getName())) {
            return authAttributes.getName();
        }
        return email;
    }

    private String resolvePicture(OAuthAttributes authAttributes) {
        if (StringUtils.hasText(authAttributes.getPicture())) {
            return authAttributes.getPicture();
        }
        return "";
    }

    private String resolveRoleKey(SnsUser user) {
        if (user.getRole() != null) {
            return user.getRole().getValue();
        }
        return UserRole.ROLE_USER.getValue();
    }
}