package com.study.galleryreservation.controller;

import com.study.galleryreservation.domain.gallery.Gallery;
import com.study.galleryreservation.domain.session.SessionUser;
import com.study.galleryreservation.dto.reservation.ReservationCreateRequestDto;
import com.study.galleryreservation.repository.GalleryRepository;
import com.study.galleryreservation.service.GalleryService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequiredArgsConstructor
public class GalleryController {

    private final GalleryRepository galleryRepository;
    private final GalleryService galleryService;


    //갤러리 상세화면
    @GetMapping("/gallery/detail")
    public String galleryDetail(@RequestParam("id") Long id, Model model) {
        Gallery gallery = galleryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("gallery", gallery);
        return "gallery/detail";

    //private final GalleryService galleryService;
    //
    //@GetMapping("/gallery/")
    //public String galleryList(Model model) {
    //    model.addAttribute("galleries", galleryService.findAll());
    //    return "list";

    }

    //갤러리 전시 예약 등록
    @PostMapping("/gallery/detail")
    public String postForm(@ModelAttribute ReservationCreateRequestDto requestDto,
                           HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        galleryService.save(requestDto, sessionUser.getEmail());
        return "redirect:/reservation/list";
    }
}
