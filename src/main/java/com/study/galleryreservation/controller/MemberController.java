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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


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

    /**
     * 회원가입 제출 처리.
     * <ul>
     *   <li>변경: {@code @ResponseBody} 제거 — HTML 응답이 아니라 뷰 이름 또는 redirect로 반환.</li>
     *   <li>변경: {@code BindingResult} 오류 시 {@code member/join} 재렌더 — Thymeleaf에서 필드별 경고 표시.</li>
     *   <li>변경: 성공 시 {@code redirect:/member/login?joined=true} — 로그인 화면의 기존 초록 안내 영역 사용.</li>
     *   <li>변경: {@code IllegalArgumentException} 등은 {@code signupError}로 모델에 넣어 동일 페이지 상단 경고 박스에 표시.</li>
     * </ul>
     */
    @PostMapping("/member/join")
    public String signupAction(
            @Valid @ModelAttribute("memberJoinRequestDto") MemberJoinRequestDto dto,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "member/join";
        }
        try {
            service.signup(dto);
            // 변경: 가입 완료 alert 대신 로그인 페이지로 이동 후 param.joined 로 안내 (login.html 기존 마크업 활용)
            redirectAttributes.addAttribute("joined", "true");
            return "redirect:/member/login";
        } catch (IllegalArgumentException e) {
            // 변경: 중복 아이디 등 — alert 대신 같은 폼 상단에 signupError 로 표시
            model.addAttribute("signupError", e.getMessage());
            return "member/join";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("signupError", "회원가입 중 오류가 발생했습니다.");
            return "member/join";
        }
    }
}
