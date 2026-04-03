package com.study.galleryreservation.controller;

import com.study.galleryreservation.domain.reservation.Reservation;
import com.study.galleryreservation.domain.session.SessionUser;
import com.study.galleryreservation.dto.reservation.ReservationCreateRequestDto;
import com.study.galleryreservation.dto.reservation.ReservationResponseDto;
import com.study.galleryreservation.repository.GalleryRepository;
import com.study.galleryreservation.service.GalleryService;
import com.study.galleryreservation.service.ReservationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

    private final GalleryRepository galleryRepository;
    private final GalleryService galleryService;
    private final ReservationService reservationService;

    // 예약 리스트 패이지
    @GetMapping("/list")
    public String reservationList(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "") String keyword,
                                  Model model, HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        Page<ReservationResponseDto> reservationPage = reservationService.findByEmailPage(sessionUser.getEmail(), page, keyword);
        model.addAttribute("page", reservationPage);
        model.addAttribute("keyword", keyword);
        return "reservation/list";
    }

    // 예약 상세 페이지
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, HttpSession session, Model model) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        Reservation reservation = reservationService.findByIdAndEmail(id, sessionUser.getEmail());
        model.addAttribute("reservation", ReservationResponseDto.from(reservation));
        model.addAttribute("gallery", reservation.getGallery());
        return "reservation/reservation-detail";
    }

    // 예약 취소하기
    @PostMapping("/cancel/{id}")
    public String cancel(@PathVariable Long id, HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        reservationService.cancel(id, sessionUser.getEmail());
        return "redirect:/reservation/list";
    }

}