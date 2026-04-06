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

    @NotBlank(message = "아이디를 입력해 주세요.")
    @Size(min = 5, max = 20, message = "아이디는 5자 이상 20자 이하로 입력해 주세요.")
    private String username;


    @NotBlank(message = "비밀번호를 입력해 주세요.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해 주세요.")
    private String password;

    private String email;
    private MemberRole role;
    private LocalDateTime created_at;
}
