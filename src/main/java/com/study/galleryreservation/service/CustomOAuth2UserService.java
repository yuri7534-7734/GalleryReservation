package com.study.galleryreservation.service;

import com.study.galleryreservation.config.OAuthAttributes;
import com.study.galleryreservation.domain.session.SessionUser;
import com.study.galleryreservation.domain.session.SnsUser;
import com.study.galleryreservation.domain.session.UserRole;
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

        SnsUser user = saveOrUpdate(attribute);
        httpSession.setAttribute("user", new SessionUser(user));

        String nameKey = attribute.getNameAttributeKey();
        Object nameValue = attribute.getAttributes().get(nameKey);
        if (nameValue == null) {
            throw new OAuth2AuthenticationException(
                    "Missing OAuth2 name attribute. registrationId=" + registrationId + ", key=" + nameKey
            );
        }

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

    private String resolveRoleKey(SnsUser user) {
        if (user.getRole() != null) {
            return user.getRole().getValue();
        }
        return UserRole.ROLE_USER.getValue();
    }
}