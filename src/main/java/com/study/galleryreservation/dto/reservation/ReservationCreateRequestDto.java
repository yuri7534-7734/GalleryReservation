package com.study.galleryreservation.dto.reservation;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationCreateRequestDto {

    @NotNull(message = "갤러리를 선택해주세요")
    private Long galleryId;

    @NotNull(message = "예약 날짜를 입력해주세요")
    private LocalDate reservationDate;

    @NotNull(message = "시작 시간을 입력해주세요")
    private LocalTime startTime;

    @NotNull(message = "종료 시간을 입력해주세요")
    private LocalTime endTime;

    @NotNull(message = "인원을 확인해주세요")
    private Integer guests;

    @NotNull(message = "연락처를 입력해주세요")
    private String contact;
}