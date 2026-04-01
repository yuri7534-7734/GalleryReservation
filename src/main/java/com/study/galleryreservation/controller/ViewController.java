package com.study.galleryreservation.controller;

import com.study.galleryreservation.dto.gallery.GalleryCreateRequestDto;
import com.study.galleryreservation.dto.reservation.ReservationCreateRequestDto;
import com.study.galleryreservation.dto.todo.TodoCreateRequestDto;
import com.study.galleryreservation.repository.GalleryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ViewController {
    private final GalleryRepository galleryRepository;

    // 메인 페이지
    @GetMapping("/")
    public String index() {
        return "index";
    }

    // 전시 목록 페이지 이동
    @GetMapping("/gallery/list")
    public String galleryList(Model model){
        model.addAttribute("galleries", galleryRepository.findAll());
        return "gallery/list";
    }


    // 예약 내역 페이지 이동
    @GetMapping("/reservation/list")
    public String reservationList(){
        return "reservation/list";
    }

    // 할일 페이지 이동
    @GetMapping("/todo/list")
    public String todoList(){
        return "todo/list";
    }

    // 새 할일 페이지 이동
    @GetMapping("/todo/form")
    public String todoForm(Model model){
        model.addAttribute("todoCreateRequestDto", new TodoCreateRequestDto());
        return "todo/form";
    }
}
