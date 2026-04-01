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
    public void save(ReservationCreateRequestDto requestDto, String email) {

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()->new IllegalArgumentException("회원을 찾을 수 없습니다."));

        Gallery gallery = galleryRepository.findById(requestDto.getGalleryId())
                .orElseThrow(()->new IllegalArgumentException("갤러리를 찾을 수 없습니다."));


        Reservation reservation = Reservation.builder()
                .member(member)
                .gallery(gallery)
                .reservationDate(requestDto.getReservationDate())
                .startTime(requestDto.getStartTime())
                .endTime(requestDto.getEndTime())
                .guests(requestDto.getGuests())
                .contact(requestDto.getContact())
                .status(ReservationStatus.PENDING)
                .build();

        reservationRepository.save(reservation);
    }

    //로그인한 유저의 예약 목록을 DTO로 변환해서 화면에 넘겨주는 메서드
    public List<ReservationResponseDto> findByEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()-> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        return reservationRepository.findByMemberOrderByCreatedAtAsc(member)
                .stream()
                .map(ReservationResponseDto::from)
                .toList();

    }
    //예약 삭제
    @Transactional
    public void cancel(Long id, String email){
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("예약을 찾을 수 없습니다."));

       if (!reservation.getMember().getEmail().equals(email)) {
           throw new IllegalArgumentException("본인 예약만 취소할 수 있습니다.");
       }
       if (reservation.getStatus() != ReservationStatus.PENDING) {
           throw new IllegalArgumentException("대기 중인 예약만 취소할 수 있습니다.");
       }

       reservation.cancel();
    }
    //예약 확정
    @Transactional
    public void approved(Long id){
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("예약을 찾을 수 없습니다."));
        if (reservation.getStatus() != ReservationStatus.PENDING){
            throw new IllegalArgumentException("대기 중인 예약만 확정할 수 있습니다.");
        }
        reservation.approved();
    }

    //예약 거절
    @Transactional
    public void rejected(Long id){
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("예약을 찾을 수 없습니다."));
        if (reservation.getStatus() != ReservationStatus.PENDING){
            throw new IllegalArgumentException("대기 중인 예약만 거절할 수 있습니다.");
        }
        reservation.rejected();
    }

    public List<ReservationResponseDto> findAllByOrderByIdAsc(){

        return reservationRepository.findAllByOrderByIdAsc()
                .stream()
                .map(ReservationResponseDto::from)
                .toList();

    }



}

