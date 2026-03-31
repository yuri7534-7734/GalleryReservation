package com.study.galleryreservation.controller;

//import com.study.galleryreservation.service.ReservationService;

import com.study.galleryreservation.dto.reservation.ReservationCreateRequestDto;
import com.study.galleryreservation.repository.GalleryRepository;
import com.study.galleryreservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

    private final GalleryRepository galleryRepository;
    private final ReservationService reservationService;

    @GetMapping("/form")
    public String getForm(Model model){
        model.addAttribute("reservationCreateRequestDto", new ReservationCreateRequestDto());
        model.addAttribute("galleries", galleryRepository.findAll());
        return "reservation/form";
    }
    @PostMapping("/form")
    public String postForm(@ModelAttribute ReservationCreateRequestDto requestDto){

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        reservationService.save(requestDto, username);

        return "redirect:/reservation/list";
    }

    @GetMapping("/list")
    public String getList(){
        return "reservation/list";
    }
}
