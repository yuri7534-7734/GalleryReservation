package com.study.galleryreservation.dto.member;

import com.study.galleryreservation.domain.member.Member;
import com.study.galleryreservation.domain.member.MemberRole;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class MemberJoinRequestDto {
    private String username;
    private String password;
    private String email;
    private MemberRole role;
    private LocalDateTime created_at;
}
