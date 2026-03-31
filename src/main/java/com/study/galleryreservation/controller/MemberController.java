package com.study.galleryreservation.controller;

//import com.study.galleryreservation.service.MemberService;
import com.study.galleryreservation.dto.member.MemberJoinRequestDto;
import com.study.galleryreservation.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService service;

    // 로그인 페이지 이동
    @GetMapping("/member/login")
    public String login(){
        return "/member/login";
    }

    // 회원가입 페이지 이동
    @GetMapping("/member/join")
    public String join(Model model){
        model.addAttribute("memberJoinRequestDto", new MemberJoinRequestDto());
        return "member/join";
    }

    // 회원가입
    @PostMapping("/member/join")
    public String memberJoin(@ModelAttribute MemberJoinRequestDto dto){
        service.signup(dto);
        return "redirect:/member/login";
    }
}
