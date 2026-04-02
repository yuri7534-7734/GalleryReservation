package com.study.galleryreservation.repository;

import com.study.galleryreservation.domain.member.Member;
import com.study.galleryreservation.domain.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByMemberOrderByCreatedAtAsc(Member member);
    List<Reservation> findAllByOrderByIdAsc();
}
