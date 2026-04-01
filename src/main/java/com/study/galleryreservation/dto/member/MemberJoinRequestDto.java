package com.study.galleryreservation.dto.member;

import com.study.galleryreservation.domain.member.MemberRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class MemberJoinRequestDto {
    public String username;
    public String password;
    public String email;
    public MemberRole role;
    private LocalDateTime created_at;
}
