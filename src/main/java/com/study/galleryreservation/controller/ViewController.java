package com.study.galleryreservation.controller;

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
public class ViewController {
    private final MemberService service;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/member/login")
    public String login(){
        return "/member/login";
    }

    @GetMapping("/member/join")
    public String join(Model model){
        model.addAttribute("memberJoinRequestDto", new MemberJoinRequestDto());
        return "member/join";
    }

    @PostMapping("/member/join")
    public String memberJoin(@ModelAttribute MemberJoinRequestDto dto){
        service.signup(dto);
        return "redirect:/member/login";
    }
}
