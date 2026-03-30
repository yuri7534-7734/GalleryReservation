package com.study.galleryreservation.dto.reservation;

import com.study.galleryreservation.domain.reservation.ReservationStatus;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationCreateRequestDto {
    private Long memberId;
    private Long galleryId;
    private ReservationStatus status;

}
