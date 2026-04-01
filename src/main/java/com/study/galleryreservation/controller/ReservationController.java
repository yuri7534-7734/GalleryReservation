package com.study.galleryreservation.controller;

import com.study.galleryreservation.dto.reservation.ReservationCreateRequestDto;
import com.study.galleryreservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ReservationController {

//    private final ReservationService reservationService;
//
//    @GetMapping()
//
//    @PostMapping("/reservation/form")
//    public String createReservation(@ModelAttribute ReservationCreateRequestDto requestDto,
//                                    Principal principal) {
//        if (requestDto.getStartTime() != null) {
//            requestDto.setEndTime(requestDto.getStartTime().plusHours(2));
//        }
//        reservationService.save(requestDto, principal.getName());
//        return "redirect:/reservation/list";
//    }
}
