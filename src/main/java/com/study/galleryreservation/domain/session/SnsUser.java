package com.study.galleryreservation.domain.session;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sns_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnsUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "provider", nullable = false, length = 50)
    private String provider;

    @Column(name = "provider_id", nullable = false, length = 100)
    private String providerId;

    @Column(name = "picture", nullable = false)
    private String picture; // 프로필 이미지 url

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRole role;

    @Builder
    public SnsUser(String name, String email, String picture, String provider, String providerId, UserRole role){
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
    }

    public SnsUser update(String name, String picture, String email){
        this.name = name;
        this.picture = picture;
        this.email = email;
        return this;
    }

    public String getRoleKey(){
        return this.role.getValue(); // "ROLE_USER" OR "ROLE_ADMIN"
    }
}
