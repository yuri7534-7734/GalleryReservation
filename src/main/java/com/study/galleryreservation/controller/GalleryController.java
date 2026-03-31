package com.study.galleryreservation.controller;

import com.study.galleryreservation.service.GalleryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class GalleryController {

    private final GalleryService galleryService;

    @GetMapping("/gallery/")
    public String galleryList(Model model) {
        model.addAttribute("galleries", galleryService.findAll());
        return "list";
    }
}
