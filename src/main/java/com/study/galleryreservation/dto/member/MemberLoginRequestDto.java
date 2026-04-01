package com.study.galleryreservation.dto.member;

import com.study.galleryreservation.domain.member.MemberRole;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class MemberLoginRequestDto {
    private String username;
    private String password;
    private String email;
    private MemberRole role;
    private LocalDateTime created_at;
}
