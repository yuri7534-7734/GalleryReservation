package com.study.galleryreservation.service;

import com.study.galleryreservation.domain.gallery.Gallery;
import com.study.galleryreservation.domain.member.Member;
import com.study.galleryreservation.domain.reservation.Reservation;
import com.study.galleryreservation.domain.reservation.ReservationStatus;
import com.study.galleryreservation.dto.gallery.GalleryCreateRequestDto;
import com.study.galleryreservation.dto.gallery.GalleryResponseDto;
import com.study.galleryreservation.dto.gallery.GalleryUpdateRequestDto;
import com.study.galleryreservation.dto.reservation.ReservationCreateRequestDto;
import com.study.galleryreservation.repository.GalleryRepository;
import com.study.galleryreservation.repository.MemberRepository;
import com.study.galleryreservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GalleryService {
    private final GalleryRepository galleryRepository;
    private final MemberRepository memberRepository;
    private final ReservationRepository reservationRepository;

    // 갤러리 조회(관리자 전용)
    public List<GalleryResponseDto> findAll(){
        List<Gallery> list = galleryRepository.findAll();

        return list.stream().map(GalleryResponseDto::new)
                .collect(Collectors.toList());
    }

    // 갤러리 추가(관리자 전용)
    @Transactional
    public void galleryAdd(final GalleryCreateRequestDto dto){
        if(galleryRepository.findByName(dto.getName()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 갤러리 이름입니다.");
        }

        LocalDateTime now = LocalDateTime.now();
        LocalTime start = dto.getStartTime() != null ? dto.getStartTime() : LocalTime.of(10, 0);
        LocalTime end = dto.getEndTime() != null ? dto.getEndTime() : LocalTime.of(18, 0);
        String cover = dto.getCoverImageUrl();
        if (cover != null) {
            cover = cover.trim();
            if (cover.isEmpty()) {
                cover = null;
            }
        }
        Gallery gallery = Gallery.builder()
                .name(dto.getName())
                .location(dto.getLocation())
                .floorZone(dto.getFloorZone() != null && !dto.getFloorZone().isBlank() ? dto.getFloorZone() : "1")
                .description(dto.getDescription())
                .capacity(dto.getCapacity())
                .isActive(true)
                .startTime(dto.getStartTime() != null ? dto.getStartTime() : LocalTime.of(10, 0))
                .endTime(dto.getEndTime() != null ? dto.getEndTime() : LocalTime.of(18, 0))
                .coverImageUrl(dto.getCoverImageUrl())
                .createdAt(now)
                .updatedAt(now)
                .build();
        galleryRepository.save(gallery);
    }

    // 갤러리 단건 조회
    public Gallery findById(final Long id) {
        return galleryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("갤러리를 찾을 수 없습니다."));
    }

     // 갤러리 수정(관리자 전용)
    @Transactional
    public void update(final Long id, final GalleryUpdateRequestDto dto) {
        Gallery gallery = galleryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("갤러리를 찾을 수 없습니다."));
        gallery.update(dto.getName(), dto.getLocation(), dto.getFloorZone(),
                dto.getDescription(), dto.getCapacity(), dto.getActive(), dto.getCoverImageUrl());
    }

    // 갤러리 삭제(관리자 전용)
    @Transactional
    public void galleryDelete(final Long id){
        Gallery gallery = galleryRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("작품을 찾을 수 없어 삭제가 불가능합니다."));

        galleryRepository.delete(gallery);
    }

    //갤러리 예약 등록
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

}
