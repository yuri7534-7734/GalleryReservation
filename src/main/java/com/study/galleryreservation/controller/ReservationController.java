package com.study.galleryreservation.controller;

import com.study.galleryreservation.domain.session.SessionUser;
import com.study.galleryreservation.dto.reservation.ReservationCreateRequestDto;
import com.study.galleryreservation.dto.reservation.ReservationResponseDto;
import com.study.galleryreservation.repository.GalleryRepository;
import com.study.galleryreservation.service.ReservationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

    private final GalleryRepository galleryRepository;
    private final ReservationService reservationService;

    @GetMapping("/form")
    public String getForm(Model model) {
        model.addAttribute("reservationCreateRequestDto", new ReservationCreateRequestDto());
        model.addAttribute("galleries", galleryRepository.findAll());
        return "reservation/form";
    }

    @PostMapping("/form")
    public String postForm(@ModelAttribute ReservationCreateRequestDto requestDto,
                           HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        reservationService.save(requestDto, sessionUser.getEmail());
        return "redirect:/reservation/list";
    }

    @GetMapping("/list")
    public String reservationList(Model model, HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        List<ReservationResponseDto> reservation = reservationService.findByEmail(sessionUser.getEmail());
        model.addAttribute("reservations", reservation);
        return "reservation/list";
    }

    @PostMapping("/cancel/{id}")
    public String cancel(@PathVariable Long id, HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        reservationService.cancel(id, sessionUser.getEmail());
        return "redirect:/reservation/list";
    }
}
