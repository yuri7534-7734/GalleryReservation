package com.study.galleryreservation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    // 메인 페이지
    @GetMapping("/")
    public String index() {
        return "index";
    }

    // 전시 목록 페이지 이동
    @GetMapping("/gallery/list")
    public String galleryList(){
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

    // 갤러리 관리
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
