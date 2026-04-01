package com.study.galleryreservation.controller;

import com.study.galleryreservation.domain.member.Member;
import com.study.galleryreservation.dto.todo.TodoCreateRequestDto;
import com.study.galleryreservation.repository.MemberRepository;
import com.study.galleryreservation.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class TodoController {
}
