package com.study.galleryreservation.controller;

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

}
