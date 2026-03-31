package com.study.galleryreservation.dto.member;

import com.study.galleryreservation.domain.member.MemberRole;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberResponseDto {
    private Long id;
    public String username;
    public String password;
    public String email;
    public MemberRole role;
    private LocalDateTime created_at;
}
