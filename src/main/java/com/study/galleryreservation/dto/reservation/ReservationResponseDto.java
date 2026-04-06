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
    private Long galleryId;
    private String galleryName;
    private String memberName;
    private String email;
    private LocalDate reservationDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer guests;
    private String contact;
    private ReservationStatus status;

    public static ReservationResponseDto from(Reservation reservation) {
        return ReservationResponseDto.builder()
                .id(reservation.getId())
                .galleryId(reservation.getGallery().getId())
                .galleryName(reservation.getGallery().getName())
                .memberName(reservation.getMember().getUsername())
                .email(reservation.getMember().getEmail())
                .reservationDate(reservation.getReservationDate())
                .startTime(reservation.getStartTime())
                .endTime(reservation.getEndTime())
                .guests(reservation.getGuests())
                .contact(reservation.getContact())
                .status(reservation.getStatus())
                .build();
    }
}

