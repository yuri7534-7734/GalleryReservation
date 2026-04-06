package com.study.galleryreservation.service;

import com.study.galleryreservation.domain.member.Member;
import com.study.galleryreservation.domain.reservation.Reservation;
import com.study.galleryreservation.domain.reservation.ReservationStatus;
import com.study.galleryreservation.dto.reservation.ReservationResponseDto;
import com.study.galleryreservation.repository.MemberRepository;
import com.study.galleryreservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {
    public final ReservationRepository reservationRepository;
    public final MemberRepository memberRepository;

    // 전시 예약 리스트 10개씩 조회(관리자 전용)
    public Page<Reservation> getList(int page, String keyword) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").descending());
        if (keyword != null && !keyword.isBlank()) {
            return reservationRepository.findByGallery_NameContainingIgnoreCase(keyword, pageable);
        }
        return reservationRepository.findAll(pageable);
    }

    // 로그인한 유저의 예약 목록을 DTO 페이징으로 조회
    public Page<ReservationResponseDto> findByEmailPage(String email, int page, String keyword) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        Pageable pageable = PageRequest.of(Math.max(page, 0), 10, Sort.by("createdAt").descending());
        if (keyword != null && !keyword.isBlank()) {
            return reservationRepository.findByMemberAndGallery_NameContainingIgnoreCase(member, keyword, pageable)
                    .map(ReservationResponseDto::from);
        }
        return reservationRepository.findByMember(member, pageable)
                .map(ReservationResponseDto::from);
    }

    //로그인한 유저의 예약 목록을 DTO로 변환해서 화면에 넘겨주는 메서드
    public List<ReservationResponseDto> findByEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        return reservationRepository.findByMemberOrderByCreatedAtAsc(member)
                .stream()
                .map(ReservationResponseDto::from)
                .toList();

    }

    // 예약 삭제
    @Transactional
    public void cancel(Long id, String email) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("예약을 찾을 수 없습니다."));

        if (!reservation.getMember().getEmail().equals(email)) {
            throw new IllegalArgumentException("본인 예약만 취소할 수 있습니다.");
        }
        if (reservation.getStatus() != ReservationStatus.PENDING) {
            throw new IllegalArgumentException("대기 중인 예약만 취소할 수 있습니다.");
        }

        reservation.cancel();
    }

    // 예약 확정
    @Transactional
    public void approved(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("예약을 찾을 수 없습니다."));
        if (reservation.getStatus() != ReservationStatus.PENDING) {
            throw new IllegalArgumentException("대기 중인 예약만 확정할 수 있습니다.");
        }
        reservation.approved();
    }

    // 예약 거절
    @Transactional
    public void rejected(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("예약을 찾을 수 없습니다."));
        if (reservation.getStatus() != ReservationStatus.PENDING) {
            throw new IllegalArgumentException("대기 중인 예약만 거절할 수 있습니다.");
        }
        reservation.rejected();
    }

    public List<ReservationResponseDto> findAllByOrderByIdAsc() {
        return reservationRepository.findAllByOrderByIdAsc()
                .stream()
                .map(ReservationResponseDto::from)
                .toList();
    }

    // 예약 단건 조회(상세보기)
    public Reservation findByIdAndEmail(final Long id, final String email) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("예약을 찾을 수 없습니다."));
        if (!reservation.getMember().getEmail().equals(email)) {
            throw new IllegalArgumentException("본인 예약만 조회할 수 있습니다.");
        }
        return reservation;
    }
}
