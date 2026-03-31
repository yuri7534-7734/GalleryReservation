package com.study.galleryreservation.service;

import com.study.galleryreservation.domain.gallery.Gallery;
import com.study.galleryreservation.domain.reservation.Reservation;
import com.study.galleryreservation.domain.reservation.ReservationStatus;
import com.study.galleryreservation.domain.session.SnsUser;
import com.study.galleryreservation.dto.reservation.ReservationCreateRequestDto;
import com.study.galleryreservation.repository.GalleryRepository;
import com.study.galleryreservation.repository.MemberRepository;
import com.study.galleryreservation.repository.ReservationRepository;
import com.study.galleryreservation.repository.SnsUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {

    public final ReservationRepository reservationRepository;
    public final MemberRepository memberRepository;
    public final SnsUserRepository snsUserRepository;
    public final GalleryRepository galleryRepository;

    @Transactional
    public void save(ReservationCreateRequestDto requestDto, String username) {

        System.out.println("=== username 확인: " + username + " ===");

        SnsUser snsUser = snsUserRepository.findByProviderId(username)
                .orElseThrow(()->new IllegalArgumentException("회원을 찾을 수 없습니다."));

        Gallery gallery = galleryRepository.findById(requestDto.getGalleryId())
                .orElseThrow(()->new IllegalArgumentException("갤러리를 찾을 수 없습니다."));


        Reservation reservation = Reservation.builder()
                .snsUser(snsUser)
                .gallery(gallery)
                .reservationDate(requestDto.getReservationDate())
                .startTime(requestDto.getStartTime())
                .endTime(requestDto.getEndTime())
                .status(ReservationStatus.PENDING)
                .build();

        reservationRepository.save(reservation);
    }
}


