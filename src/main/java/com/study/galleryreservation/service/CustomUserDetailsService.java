package com.study.galleryreservation.service;

import com.study.galleryreservation.domain.member.Member;
import com.study.galleryreservation.domain.member.MemberRole;
import com.study.galleryreservation.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = repository.findByUsername(username).orElseThrow(()->
                new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        List<GrantedAuthority> authorityList = new ArrayList<>();
        MemberRole role = member.getRole() != null ? member.getRole() : MemberRole.ROLE_USER;
        authorityList.add(new SimpleGrantedAuthority(role.getValue()));

        return new User(member.username, member.password, authorityList);
    }
}
