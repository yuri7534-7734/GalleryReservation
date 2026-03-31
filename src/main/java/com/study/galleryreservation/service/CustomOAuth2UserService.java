package com.study.galleryreservation.service;

import com.study.galleryreservation.config.OAuthAttributes;
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

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final HttpSession httpSession;
    private final SnsUserRepository repository;
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

        // 2. delegate를 통해 실제로 구글/카카오에 유저정보 요청
        //    {name, email, picture} 같은 정보가 담겨있음
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 3. 어떤 플랫폼으로 로그인했는지 구분
        //    "google" or "kakao" or "naver" 문자열이 들어옴
        // registrationId : Google, Kakao, Naver, GitHub, Apple
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // 4. 각 플랫폼마다 유저를 구분하는 키 이름이 다름
        //    구글 : "sub"
        //    카카오 : "id"
        //    네이버 : "response"
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        // 5. 플랫폼마다 유저정보 구조가 다르기 때문에
        //    OAuthAttributes로 통일된 형태로 변환
        //    (구글이든 카카오든 name/email/picture로 꺼낼 수 있게)
        OAuthAttributes attribute = OAuthAttributes.of(registrationId, userNameAttributeName,
                oAuth2User.getAttributes());

        // 6. DB에 저장 or 업데이트
        //    신규 유저 → INSERT
        //    기존 유저 → UPDATE
        SnsUser user = saveOrUpdate(attribute);

        // 세션에 유저 정보 저장 --> 이거 수정해야함.
        httpSession.setAttribute("user", UserRole.valueOf("ROLE_USER"));

        // Spring Security에게 "이 유저는 인증된 유저야" 라고 알려주는 객체 반환
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())), // 권한
                attribute.getAttributes(),       // 유저 정보
                attribute.getNameAttributeKey()  // 유저 구분 키
        );
    }

    private SnsUser saveOrUpdate(OAuthAttributes authAttributes) {

        // 어떤 플랫폼으로 로그인했는지
        //    "google" or "kakao" or "naver"
        String provider = authAttributes.getRegistrationId();

        // 2. 각 플랫폼에서 발급한 고유 ID
        //    구글: "118234567890"
        //    카카오: "3234567890"
        String providerId = authAttributes.getProviderId();

        // 3. 이메일이 없는 경우 대비해서 별도 메서드로 처리
        //    (카카오는 이메일 제공 안 할 수도 있음)
        String email = resolveEmail(authAttributes);

        // 4. 이름이 없는 경우 이메일을 이름으로 대체
        String name = resolveName(authAttributes, email);

        // 5. 프로필 사진이 없는 경우 빈 문자열로 대체
        String picture = resolvePicture(authAttributes);

        // 6. provider + providerId로 DB에서 기존 유저 조회
        //    이메일로 찾지 않는 이유 :
        //    같은 이메일로 구글/카카오 둘 다 가입한 경우를 구분해야 하기 때문
        SnsUser snsUser = repository.findByProviderAndProviderId(provider, providerId)
                // 7. 기존 유저가 있으면 → 이름/사진/이메일 업데이트
                .map(entity -> entity.update(name, picture, email))
                // 8. 기존 유저가 없으면 → 새로운 유저 생성
                .orElse(SnsUser.builder()
                        .name(name)
                        .email(email)
                        .picture(picture)
                        .provider(provider)    // "google"
                        .providerId(providerId) // "118234567890"
                        .role(UserRole.ROLE_USER)   // 기본 권한은 USER
                        .build());

        // 9. DB에 저장
        return repository.save(snsUser);
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
    }

