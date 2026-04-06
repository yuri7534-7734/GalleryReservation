package com.study.galleryreservation.controller;

import com.study.galleryreservation.domain.gallery.Gallery;
import com.study.galleryreservation.domain.session.SessionUser;
import com.study.galleryreservation.dto.reservation.ReservationCreateRequestDto;
import com.study.galleryreservation.repository.GalleryRepository;
import com.study.galleryreservation.service.GalleryService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class GalleryController {

    private final GalleryRepository galleryRepository;
    private final GalleryService galleryService;

    // 갤러리 전시 작품 상세 보기
    @GetMapping("/gallery/detail")
    public String galleryDetail(@RequestParam("id") Long id, Model model) {
        Gallery gallery = galleryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("gallery", gallery);
        model.addAttribute("visitTimeSlots", halfHourSlots(gallery.getStartTime(), gallery.getEndTime()));
        return "gallery/detail";
    }

    /**
     * start(포함) ~ end(미포함) 구간을 30분 간격으로 나눈 관람 선택 시간 목록
     */
    static List<LocalTime> halfHourSlots(LocalTime start, LocalTime end) {
        List<LocalTime> slots = new ArrayList<>();
        if (start == null || end == null || !end.isAfter(start)) {
            return slots;
        }
        for (LocalTime t = start; t.isBefore(end); t = t.plusMinutes(30)) {
            slots.add(t);
        }
        return slots;
    }

    //갤러리 예약 등록
    @PostMapping("/gallery/detail")
    public String postForm(@Valid @ModelAttribute ReservationCreateRequestDto requestDto,
                           HttpSession session, BindingResult bindingResult, Model model) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        if (bindingResult.hasErrors()) {
            model.addAttribute("contactError", "올바른 연락처 형식이 아닙니다.");
            //페이지로 돌아왔을 때는 post 요청이기 때문에 모델이 비어있는 상태로 돌아오기 때문에 깨질 수 있어서
            //반환시 model에 gallery도 담는다.
            model.addAttribute("gallery", galleryRepository.findById(requestDto.getGalleryId()).orElseThrow());
            return "gallery/detail";
        }

            galleryService.save(requestDto, sessionUser.getEmail());
            return "redirect:/reservation/list";


    }
}