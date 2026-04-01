package com.study.galleryreservation.controller;

import com.study.galleryreservation.dto.reservation.ReservationCreateRequestDto;
import com.study.galleryreservation.dto.todo.TodoCreateRequestDto;
import com.study.galleryreservation.repository.GalleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @Autowired
    private GalleryRepository galleryRepository;

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

    // 예약 폼 페이지 이동
    @GetMapping("/reservation/form")
    public String reservationForm(Model model){
        model.addAttribute("reservationCreateRequestDto", new ReservationCreateRequestDto());
        model.addAttribute("galleries", galleryRepository.findAll());
        return "reservation/form";
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

    // 갤러리 관리(관리자 전용)
    @GetMapping("/admin/gallery/list")
    public String adminGalleryList(){
        return "admin/gallery-list";
    }

    // 갤러리 등록(관리자 전용)
    @GetMapping("/admin/gallery/form")
    public String adminGalleryForm(){
        return "admin/gallery-form";
    }
}
