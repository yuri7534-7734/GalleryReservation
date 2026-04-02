package com.study.galleryreservation.controller;

import com.study.galleryreservation.domain.gallery.Gallery;
import com.study.galleryreservation.dto.gallery.GalleryCreateRequestDto;
import com.study.galleryreservation.dto.gallery.GalleryUpdateRequestDto;
import com.study.galleryreservation.service.GalleryService;
import com.study.galleryreservation.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final GalleryService galleryService;
    private final ReservationService reservationService;


    // 갤러리 관리 페이지 이동(관리자 전용)
    @GetMapping("/gallery/list")
    public String adminGalleryList(Model model){
        model.addAttribute("galleries", galleryService.findAll());
        return "admin/gallery-list";
    }

    // 갤러리 등록 페이지 이동(관리자 전용)
    @GetMapping("/gallery/form")
    public String adminGalleryFormPage(Model model){
        model.addAttribute("galleryCreateRequestDto", new GalleryCreateRequestDto());
        return "admin/gallery-form";
    }

     // 겔러리 등록
    @PostMapping("/gallery/form")
    public String adminGalleryForm(@ModelAttribute GalleryCreateRequestDto dto){
        galleryService.galleryAdd(dto);
        return "redirect:/admin/gallery/list";
    }

    // 갤러리 삭제
    @PostMapping("/gallery/delete/{id}")
    public String adminGalleryDelete(@PathVariable Long id){
        galleryService.galleryDelete(id);
        return "redirect:/admin/gallery/list";
    }

    // 전체 예약 목록(관리자 전용)
    @GetMapping("/reservation/list")
    public String reservationList(Model model){
        model.addAttribute("reservations",reservationService.findAllByOrderByIdAsc());

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

     // 갤러리 수정 페이지 이동
    @GetMapping("/gallery/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Gallery gallery = galleryService.findById(id);
        model.addAttribute("gallery", gallery);
        model.addAttribute("galleryUpdateRequestDto", GalleryUpdateRequestDto.from(gallery));
        return "admin/gallery-edit";
    }

     // 갤러리 수정
    @PostMapping("/gallery/edit/{id}")
    public String edit(@PathVariable Long id,
                       @Valid @ModelAttribute GalleryUpdateRequestDto dto,
                       BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("gallery", galleryService.findById(id));
            return "admin/gallery-edit";
        }
        galleryService.update(id, dto);
        return "redirect:/admin/gallery/list";
    }
}
