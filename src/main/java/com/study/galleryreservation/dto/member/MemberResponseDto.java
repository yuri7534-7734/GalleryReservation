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
    private String username;
    private String password;
    private String email;
    private MemberRole role;
    private LocalDateTime created_at;
}
