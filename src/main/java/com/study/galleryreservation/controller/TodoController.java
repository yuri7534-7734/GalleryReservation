package com.study.galleryreservation.controller;

import com.study.galleryreservation.domain.member.Member;
import com.study.galleryreservation.dto.todo.TodoCreateRequestDto;
import com.study.galleryreservation.dto.todo.TodoResponseDto;
import com.study.galleryreservation.dto.todo.TodoUpdateRequestDto;
import com.study.galleryreservation.repository.MemberRepository;
import com.study.galleryreservation.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    // 목록 조회
    @GetMapping("/list")
    public String list(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Boolean isDone,
            @RequestParam(defaultValue = "0") int page,
            Principal principal,
            Model model) {

        Pageable pageable = PageRequest.of(page, 7);

        Member member = memberRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("로그인 사용자를 찾을 수 없습니다."));
        Long memberId = member.getId();

        Page<TodoResponseDto> todos;

        if (keyword != null && !keyword.isBlank()) {
            todos = todoService.search(memberId, keyword, pageable);
        } else if (isDone != null) {
            todos = todoService.getAllByIsDone(memberId, isDone, pageable);
        } else {
            todos = todoService.getAll(memberId, pageable);
        }

        model.addAttribute("todos", todos);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", todos.getTotalPages());

        return "todo/list";
    }

    // 작성 폼
    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("todoCreateRequestDto", new TodoCreateRequestDto());
        return "todo/form";
    }

    // 생성
    @PostMapping("/create")
    public String create(@ModelAttribute TodoCreateRequestDto dto, Principal principal) {
        Member member = memberRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("로그인 사용자를 찾을 수 없습니다."));
        todoService.create(member.getId(), dto);
        return "redirect:/todo/list";
    }

    // 수정 폼
    @GetMapping("/update/{id}")
    public String updateForm(
            @PathVariable Long id,        // ← @Valid 제거
            Principal principal,
            Model model,
            RedirectAttributes redirectAttributes) {

        Member member = memberRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("로그인 사용자를 찾을 수 없습니다."));
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

    // 수정 처리
    @PostMapping("/update/{id}")
    public String update(
            @PathVariable Long id,        // ← @Valid 제거
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

    // 삭제
    @PostMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id,
            Principal principal,
            RedirectAttributes redirectAttributes) {

        Member member = memberRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("로그인 사용자를 찾을 수 없습니다."));
        try {
            todoService.delete(id, member.getId());
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/todo/list";
        }
        return "redirect:/todo/list";
    }
}