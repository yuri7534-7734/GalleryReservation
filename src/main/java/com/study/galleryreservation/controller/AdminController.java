package com.study.galleryreservation.controller;

//import com.study.galleryreservation.service.GalleryService;
//import com.study.galleryreservation.service.ReservationService;

import com.study.galleryreservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final ReservationService reservationService;

    // 전체 예약 목록(관리자 전용)
    @GetMapping("/reservation/list")
    public String reservationList(Model model){
        model.addAttribute("reservations",reservationService.findAll());

        return "admin/reservation-list";
    }

    @PostMapping("/reservation/approve/{id}")
    public String reservationApprove(@PathVariable Long id){
        reservationService.approved(id);
        return "redirect:/admin/reservation/list";
    }

    @PostMapping("/reservation/reject/{id}")
    public String reservationReject(@PathVariable Long id){
        reservationService.rejected(id);
        return "redirect:/admin/reservation/list";
    }
}
