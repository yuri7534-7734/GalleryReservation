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
    Page<Reservation> findByMemberAndGallery_NameContainingIgnoreCase(Member member, String keyword, Pageable pageable);
    Page<Reservation> findByGallery_NameContainingIgnoreCase(String keyword, Pageable pageable);
    List<Reservation> findAllByOrderByIdAsc();

    //내 예약에서 조회
    @Query("SELECT r FROM Reservation r WHERE r.member = :member AND LOWER(REPLACE(r.gallery.name, ' ', '')) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Reservation> findByMemberAndGalleryNameIgnoreSpace(@Param("member") Member member, @Param("keyword") String keyword, Pageable pageable);
    //관리자 예약관리에서 조회
    @Query("SELECT r FROM Reservation r WHERE LOWER(REPLACE(r.gallery.name, ' ', '')) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Reservation> findByGalleryNameIgnoreSpace(@Param("keyword") String keyword, Pageable pageable);

}
