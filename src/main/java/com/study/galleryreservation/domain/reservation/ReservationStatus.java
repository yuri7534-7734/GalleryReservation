package com.study.galleryreservation.domain.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReservationStatus {
    PENDING("예약 대기"),   // 대기 (예약 접수)
    APPROVED("예약 확정"),  // 확정 (관리자 승인)
    REJECTED("예약 거절"),   // 거절 (관리자 거절)
    CANCELLED("예약 취소");  // 취소 (예약자 취소)

    private String value;
}
