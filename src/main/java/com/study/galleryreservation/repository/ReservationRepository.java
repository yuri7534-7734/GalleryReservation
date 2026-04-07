package com.study.galleryreservation.repository;

import com.study.galleryreservation.domain.member.Member;
import com.study.galleryreservation.domain.reservation.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByMemberOrderByCreatedAtAsc(Member member);
    Page<Reservation> findByMember(Member member, Pageable pageable);

    @Query("SELECT r FROM Reservation r WHERE LOWER(FUNCTION('replace', r.gallery.name, ' ', '')) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Reservation> searchByGalleryName(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT r FROM Reservation r WHERE r.member = :member AND LOWER(FUNCTION('replace', r.gallery.name, ' ', '')) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Reservation> searchByMemberAndGalleryName(@Param("member") Member member, @Param("keyword") String keyword, Pageable pageable);

    List<Reservation> findAllByOrderByIdAsc();
}
