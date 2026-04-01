package com.study.galleryreservation.controller;

import com.study.galleryreservation.dto.gallery.GalleryCreateRequestDto;
import com.study.galleryreservation.service.GalleryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final GalleryService galleryService;

    // 갤러리 관리 페이지 이동(관리자 전용)
    @GetMapping("/admin/gallery/list")
    public String adminGalleryList(Model model){
        model.addAttribute("galleries", galleryService.findAll());
        return "admin/gallery-list";
    }

    // 갤러리 등록 페이지 이동(관리자 전용)
    @GetMapping("/admin/gallery/form")
    public String adminGalleryFormPage(Model model){
        model.addAttribute("galleryCreateRequestDto", new GalleryCreateRequestDto());
        return "admin/gallery-form";
    }

     // 겔러리 등록
    @PostMapping("/admin/gallery/form")
    public String adminGalleryForm(@ModelAttribute GalleryCreateRequestDto dto){
        galleryService.galleryAdd(dto);
        return "redirect:/admin/gallery/list";
    }

    // 갤러리 삭제
    @PostMapping("/admin/gallery/delete/{id}")
    public String adminGalleryDelete(@PathVariable Long id){
        galleryService.galleryDelete(id);
        return "redirect:/admin/gallery/list";
    }
}
