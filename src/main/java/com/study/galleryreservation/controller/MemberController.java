package com.study.galleryreservation.controller;

import com.study.galleryreservation.dto.member.MemberJoinRequestDto;
import com.study.galleryreservation.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService service;

    // 로그인 페이지 이동
    @GetMapping("/member/login")
    public String login() {
        return "/member/login";
    }

    // 회원가입 페이지 이동
    @GetMapping("/member/join")
    public String join(Model model) {
        model.addAttribute("memberJoinRequestDto", new MemberJoinRequestDto());
        return "member/join";
    }

    // 회원가입
    @PostMapping("/member/join")
    @ResponseBody
    public String signupAction(@Valid @ModelAttribute MemberJoinRequestDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = bindingResult.getFieldError().getDefaultMessage();
            return "<script>alert('입력 오류: " + message + "'); history.back();</script>";
        }
        try {
            service.signup(dto);
            return "<script>alert('회원가입이 완료되었습니다! 로그인해 주세요.'); location.href='/member/login';</script>";
        } catch (IllegalArgumentException e) {
            return "<script>alert('" + e.getMessage() + "'); history.back();</script>";
        } catch (Exception e) {
            e.printStackTrace();
            return "<script>alert('회원가입 중 오류가 발생했습니다.'); history.back();</script>";
        }
    }
}













