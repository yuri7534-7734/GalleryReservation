package com.study.galleryreservation.dto.reservation;

import com.study.galleryreservation.domain.reservation.ReservationStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter @Builder
public class ReservationResponseDto {
    private Long memberId;
    private Long galleryId;
    private LocalDate reservationDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private ReservationStatus status;
}
