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

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class GalleryController {

    private final GalleryRepository galleryRepository;
    private final GalleryService galleryService;

    @GetMapping("/gallery/detail")
    public String galleryDetail(@RequestParam("id") Long id, Model model) {
        Gallery gallery = galleryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("gallery", gallery);
        model.addAttribute("visitTimeSlots", halfHourSlots(gallery.getStartTime(), gallery.getEndTime()));
        return "gallery/detail";
    }

    /** start(포함) ~ end(미포함) 구간을 30분 간격으로 나눈 관람 선택 시간 목록 */
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

    //갤러리 전시 예약 등록
    @PostMapping("/gallery/detail")
    public String postForm(@ModelAttribute ReservationCreateRequestDto requestDto,
                           HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        galleryService.save(requestDto, sessionUser.getEmail());
        return "redirect:/reservation/list";
    }

}
