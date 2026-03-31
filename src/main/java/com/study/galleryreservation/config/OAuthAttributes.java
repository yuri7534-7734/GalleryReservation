package com.study.galleryreservation.config;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Builder
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;

    private String registrationId; // google, kakao, naver
    private String providerId;     // 각 provider의 고유 id
    private String name;
    private String email;
    private String picture;

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if ("naver".equals(registrationId)) {
            return ofNaver(userNameAttributeName, attributes);
        }
        if ("kakao".equals(registrationId)) {
            return ofKakao(userNameAttributeName, attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        Object sub = attributes.get("sub");

        Map<String, Object> mapped = new HashMap<>();
        mapped.put("id", sub);
        mapped.put("name", attributes.get("name"));
        mapped.put("email", attributes.get("email"));
        mapped.put("picture", attributes.get("picture"));

        return OAuthAttributes.builder()
                .name((String) mapped.get("name"))
                .email((String) mapped.get("email"))
                .picture((String) mapped.get("picture"))
                .providerId(String.valueOf(mapped.get("id")))
                .registrationId("google")
                .nameAttributeKey("id")
                .attributes(mapped)
                .build();
    }

    @SuppressWarnings("unchecked")
    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        Map<String, Object> mapped = new HashMap<>();
        mapped.put("id", response != null ? response.get("id") : null);
        mapped.put("name", response != null ? response.get("name") : null);
        mapped.put("email", response != null ? response.get("email") : null);
        mapped.put("picture", response != null ? response.get("profile_image") : null);

        return OAuthAttributes.builder()
                .name((String) mapped.get("name"))
                .email((String) mapped.get("email"))
                .picture((String) mapped.get("picture"))
                .providerId(String.valueOf(mapped.get("id")))
                .registrationId("naver")
                .nameAttributeKey("id")
                .attributes(mapped)
                .build();
    }

    @SuppressWarnings("unchecked")
    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Object id = attributes.get("id");
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = kakaoAccount != null
                ? (Map<String, Object>) kakaoAccount.get("profile")
                : null;

        String name = profile != null ? (String) profile.get("nickname") : null;
        String picture = profile != null ? (String) profile.get("profile_image_url") : null;
        String email = kakaoAccount != null ? (String) kakaoAccount.get("email") : null;

        Map<String, Object> mapped = new HashMap<>();
        mapped.put("id", id);
        mapped.put("name", name);
        mapped.put("email", email);
        mapped.put("picture", picture);

        return OAuthAttributes.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .providerId(String.valueOf(id))
                .registrationId("kakao")
                .nameAttributeKey("id")
                .attributes(mapped)
                .build();
    }
}