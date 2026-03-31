package com.study.galleryreservation.dto.reservation;

import com.study.galleryreservation.domain.reservation.Reservation;
import com.study.galleryreservation.domain.reservation.ReservationStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class ReservationResponseDto {

    private Long id;
    private String galleryName;        // reservation.galleryName
    private String snsUsername;     // reservation.memberUsername
    private LocalDate reservationDate; // reservation.reservationDate
    private LocalTime startTime;       // reservation.startTime
    private ReservationStatus displayStatus; // reservation.displayStatus

    public static ReservationResponseDto from(Reservation reservation) {
        return ReservationResponseDto.builder()
                .id(reservation.getId())
                .galleryName(reservation.getGallery().getName())
                .snsUsername(reservation.getSnsUser().getName())
                .reservationDate(reservation.getReservationDate())
                .startTime(reservation.getStartTime())
                .displayStatus(reservation.getStatus())
                .build();
    }
}

