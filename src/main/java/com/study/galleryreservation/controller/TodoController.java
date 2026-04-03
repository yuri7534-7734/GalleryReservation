package com.study.galleryreservation.controller;

import com.study.galleryreservation.domain.member.Member;
import com.study.galleryreservation.dto.todo.TodoCreateRequestDto;
import com.study.galleryreservation.dto.todo.TodoResponseDto;
import com.study.galleryreservation.dto.todo.TodoUpdateRequestDto;
import com.study.galleryreservation.repository.MemberRepository;
import com.study.galleryreservation.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {
    private final TodoService todoService;
    private final MemberRepository memberRepository;

    @GetMapping("/list")
    public String list(
            @RequestParam(required = false) Long memberId,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Boolean isDone,
            @RequestParam(defaultValue = "0") int page,
            Model model) {
        Page<TodoResponseDto> todoPage = todoService.getPage(memberId, keyword, isDone, page);
        model.addAttribute("page", todoPage);
        model.addAttribute("memberId", memberId);
        model.addAttribute("keyword", keyword);
        model.addAttribute("isDone", isDone);
        return "todo/list";
    }

    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("todoCreateRequestDto", new TodoCreateRequestDto());
        return "todo/form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute TodoCreateRequestDto dto, Principal principal) {
        Member member = memberRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("로그인 사용자를 찾을 수 없습니다."));
        todoService.create(member.getId(), dto);
        return "redirect:/todo/list";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id,
                             Principal principal,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        Member member = memberRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("로그인 사용자를 찾을 수 없습니다"));
        TodoResponseDto todo = todoService.findById(id);
        if (!member.getId().equals(todo.getMemberId())) {
            redirectAttributes.addFlashAttribute("errorMessage", "본인의 할 일만 수정할 수 있습니다.");
            return "redirect:/todo/list";
        }

        TodoUpdateRequestDto todoUpdateRequestDto = TodoUpdateRequestDto.builder()
                .title(todo.getTitle())
                .content(todo.getContent())
                .dueDate(todo.getDueDate())
                .isDone(todo.isDone())
                .build();
        model.addAttribute("todo", todo);
        model.addAttribute("todoUpdateRequestDto", todoUpdateRequestDto);
        return "todo/update";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute TodoUpdateRequestDto dto,
                         Principal principal,
                         RedirectAttributes redirectAttributes) {
        Member member = memberRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("로그인 사용자를 찾을 수 없습니다."));
        try {
            todoService.update(id, member.getId(), dto);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/todo/list";
        }
        return "redirect:/todo/list";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         Principal principal,
                         RedirectAttributes redirectAttributes) {
        Member member = memberRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("로그인사용자를 찾을 수 없습니다."));
        try {
            todoService.delete(id, member.getId());
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/todo/list";
        }
        return "redirect:/todo/list";
    }
}