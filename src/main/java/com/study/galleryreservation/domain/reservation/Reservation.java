package com.study.galleryreservation.domain.reservation;

import com.study.galleryreservation.domain.gallery.Gallery;
import com.study.galleryreservation.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "reservation")
@Getter @Builder @AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; //예약한 회원 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gallery_id")
    private Gallery gallery; //예약한 갤러리 Id

    @Column(name = "reservation_date", nullable = false)
    private LocalDate reservationDate; //관람 날짜

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime; //관람 시작 시간

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime; //관람 종료 시간

    @Column(name = "guests")
    private Integer guests; //인원수

    @Column(name = "contact_info", length = 20)
    private String contact; //연락처

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status = ReservationStatus.PENDING; //예약 상태 (PENDING/APPROVED/REJECTED)

    @Column(name = "created_at")
    private LocalDateTime createdAt; //예약 신청 일시

    @Column(name = "updated_at")
    private LocalDateTime updatedAt; //예약 수정 일시(상태 변경)


    @PrePersist
    public void setCreatedAt(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void setUpdatedAt(){
        this.updatedAt = LocalDateTime.now();
    }

    //예약 취소 (PENDING 상태만 가능)
    public void cancel() {
        if (this.status != ReservationStatus.PENDING) {
            throw new IllegalArgumentException("대기 중인 예약만 취소할 수 있습니다.");
        }
        this.status = ReservationStatus.CANCELLED;
    }

    //예약 확정
    public void approved() {
        this.status = ReservationStatus.APPROVED;
    }

    //예약 거절
    public void rejected() {
        this.status = ReservationStatus.REJECTED;
    }
}
