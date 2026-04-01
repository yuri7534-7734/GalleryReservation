package com.study.galleryreservation.controller;

import com.study.galleryreservation.domain.gallery.Gallery;
import com.study.galleryreservation.repository.GalleryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class GalleryController {

    private final GalleryRepository galleryRepository;

    @GetMapping("/gallery/detail")
    public String galleryDetail(@RequestParam("id") Long id, Model model) {
        Gallery gallery = galleryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("gallery", gallery);
        model.addAttribute("visitTimeSlots", halfHourSlots(gallery.getOpenTime(), gallery.getCloseTime()));
        return "gallery/detail";
    }

    /** open(포함) ~ close(미포함) 구간을 30분 간격으로 나눈 관람 선택 시간 목록 */
    static List<LocalTime> halfHourSlots(LocalTime open, LocalTime close) {
        List<LocalTime> slots = new ArrayList<>();
        if (open == null || close == null || !close.isAfter(open)) {
            return slots;
        }
        for (LocalTime t = open; t.isBefore(close); t = t.plusMinutes(30)) {
            slots.add(t);
        }
        return slots;
    }
}
