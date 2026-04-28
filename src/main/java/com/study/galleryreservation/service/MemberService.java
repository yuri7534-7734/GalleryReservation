package com.study.galleryreservation.service;

import com.study.galleryreservation.domain.member.Member;
import com.study.galleryreservation.domain.member.MemberRole;
import com.study.galleryreservation.dto.member.MemberJoinRequestDto;
import com.study.galleryreservation.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(MemberJoinRequestDto dto){
        if (repository.findByUsername(dto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        // email 중복 체크 (필요하다면)
        if (repository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        Member member = Member.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .role(MemberRole.ROLE_USER)
                .createdAt(LocalDateTime.now())
                .build();
        repository.save(member);
    }
}// 수정
