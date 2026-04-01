package com.study.galleryreservation.service;

import com.study.galleryreservation.domain.gallery.Gallery;
import com.study.galleryreservation.domain.member.Member;
import com.study.galleryreservation.domain.reservation.Reservation;
import com.study.galleryreservation.domain.reservation.ReservationStatus;
import com.study.galleryreservation.dto.reservation.ReservationCreateRequestDto;
import com.study.galleryreservation.dto.reservation.ReservationResponseDto;
import com.study.galleryreservation.repository.GalleryRepository;
import com.study.galleryreservation.repository.MemberRepository;
import com.study.galleryreservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {

    public final ReservationRepository reservationRepository;
    public final MemberRepository memberRepository;
    public final GalleryRepository galleryRepository;

    //저장
    @Transactional
    public void save(ReservationCreateRequestDto requestDto, String username) {

        Member member = memberRepository.findByUsername(username)
                .orElseThrow(()->new IllegalArgumentException("회원을 찾을 수 없습니다."));

        Gallery gallery = galleryRepository.findById(requestDto.getGalleryId())
                .orElseThrow(()->new IllegalArgumentException("갤러리를 찾을 수 없습니다."));


        Reservation reservation = Reservation.builder()
                .member(member)
                .gallery(gallery)
                .reservationDate(requestDto.getReservationDate())
                .startTime(requestDto.getStartTime())
                .endTime(requestDto.getEndTime())
                .status(ReservationStatus.PENDING)
                .build();

        reservationRepository.save(reservation);
    }

    //로그인한 유저의 예약 목록을 DTO로 변환해서 화면에 넘겨주는 메서드
    public List<ReservationResponseDto> findByUsername(String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(()-> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        return reservationRepository.findByMember(member)
                .stream()
                .map(ReservationResponseDto::from)
                .toList();

    }
    //예약 삭제
    @Transactional
    public void cancel(Long id, String username){
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("예약을 찾을 수 없습니다."));

        reservationRepository.delete(reservation);
    }



}

