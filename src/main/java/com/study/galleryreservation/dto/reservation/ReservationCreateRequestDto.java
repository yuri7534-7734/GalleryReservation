package com.study.galleryreservation.dto.reservation;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    //전화번호 형식을 검증하는 정규식
    @Pattern(regexp = "^01[0-9]-[0-9]{3,4}-[0-9]{4}$", message = "올바른 연락처 형식이 아닙니다. (예: 010-1234-5678)")
    private String contact;
}