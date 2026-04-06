package com.study.galleryreservation.controller;

import com.study.galleryreservation.domain.gallery.Gallery;
import com.study.galleryreservation.domain.reservation.Reservation;
import com.study.galleryreservation.dto.gallery.GalleryCreateRequestDto;
import com.study.galleryreservation.dto.gallery.GalleryUpdateRequestDto;
import com.study.galleryreservation.dto.reservation.ReservationResponseDto;
import com.study.galleryreservation.service.GalleryService;
import com.study.galleryreservation.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final GalleryService galleryService;
    private final ReservationService reservationService;


    // 갤러리 관리 페이지 이동(관리자 전용)
    @GetMapping("/gallery/list")
    public String adminGalleryList(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "") String keyword,
                                   Model model){
        int currentPage = Math.max(page, 0);
        Page<Gallery> galleryPage = galleryService.getList(currentPage, keyword);
        model.addAttribute("page", galleryPage);
        model.addAttribute("keyword", keyword);
        return "admin/gallery-list";
    }

    // 갤러리 등록 페이지 이동(관리자 전용)
    @GetMapping("/gallery/form")
    public String adminGalleryFormPage(Model model){
        model.addAttribute("galleryCreateRequestDto", new GalleryCreateRequestDto());
        return "admin/gallery-form";
    }

     // 갤러리 등록
    @PostMapping("/gallery/form")
    public String adminGalleryForm(@ModelAttribute GalleryCreateRequestDto dto,
                                   RedirectAttributes redirectAttributes){
        // 갤러리 등록 중복 체크
        try {
            galleryService.galleryAdd(dto);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/gallery/form";
        }
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
    public String reservationList(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "") String keyword,
                                  Model model){
        int currentPage = Math.max(page, 0);
        Page<ReservationResponseDto> reservationPage = reservationService.getList(currentPage, keyword)
                .map(ReservationResponseDto::from);
        model.addAttribute("page", reservationPage);
        model.addAttribute("keyword", keyword);
        return "admin/reservation-list";
    }

    // 예약 승인
    @PostMapping("/reservation/approve/{id}")
    public String reservationApprove(@PathVariable Long id){
        reservationService.approved(id);
        return "redirect:/admin/reservation/list";
    }

    // 예약 거절
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

        try {
            galleryService.update(id, dto); //업데이트 시도
        } catch (DataIntegrityViolationException e) { //DB 제약 위반 에러 발생 시 잡음
            String messege = e.getMessage();
            if (messege.contains("description")){ //에러 메세지에 "description"이 있으면 "description" 필드 에러 등록
                bindingResult.rejectValue("description", "tooLong", "설명이 너무 깁니다. (255자 이내)");
            } else if (messege.contains("cover_image_url")) {
                bindingResult.rejectValue("cover_image_url", "tooLong", "이미지 URL이 너무 깁니다.");
            }else if (messege.contains("name")) {
                bindingResult.rejectValue("name", "tooLong", "갤러리명이 너무 깁니다.");
            }else if (messege.contains("location")) {
                bindingResult.rejectValue("location", "tooLong", "위치명이 너무 깁니다.");
            } else {
                bindingResult.reject("saveFailed", "저장 중 오류가 발생했습니다.");
            }
        }

        return "redirect:/admin/gallery/list";
    }
}
