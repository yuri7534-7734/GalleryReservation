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
}
