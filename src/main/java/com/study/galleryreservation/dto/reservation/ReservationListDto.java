package com.study.galleryreservation.dto.reservation;


import com.study.galleryreservation.domain.reservation.Reservation;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter @Setter
public class ReservationListDto  {
    private Long id;
    private String galleryName;
    private LocalTime startTime;
    private LocalTime endTime;
    private String status;
    private String contact;
    private Integer guests;

    public static ReservationListDto from(Reservation reservation){
        ReservationListDto dto = new ReservationListDto();
        dto.id = reservation.getId();
        dto.galleryName = reservation.getMember().getUsername();
        dto.status = reservation.getStatus().name();
        dto.guests = reservation.getGuests();
        dto.contact = reservation.getContact();
        return dto;
    }
}
