package com.study.galleryreservation.dto.member;

import com.study.galleryreservation.domain.member.MemberRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class MemberJoinRequestDto {
    private String username;
    @NotBlank
    @Size(min = 8, message = "비밀번호는 8자 이상")
    private String password;
    private String email;
    private MemberRole role;
    private LocalDateTime created_at;
}
