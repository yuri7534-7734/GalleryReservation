package com.study.galleryreservation.controller;

import com.study.galleryreservation.domain.reservation.Reservation;
import com.study.galleryreservation.domain.session.SnsUser;
import com.study.galleryreservation.dto.reservation.ReservationResponseDto;
import com.study.galleryreservation.repository.GalleryRepository;
import com.study.galleryreservation.repository.ReservationRepository;
import com.study.galleryreservation.service.ReservationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ViewController {
    @Autowired
    private GalleryRepository galleryRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ReservationService reservationService;

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

    // 할일 페이지 이동
    @GetMapping("/todo/list")
    public String todoList(){
        return "todo/list";
    }

    // 새 할일 페이지 이동
    @GetMapping("/todo/form")
    public String todoForm(){
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
