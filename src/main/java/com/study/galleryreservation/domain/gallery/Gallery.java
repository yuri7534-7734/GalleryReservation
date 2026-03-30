package com.study.galleryreservation.domain.gallery;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "gallery")
@Getter @Builder @AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Gallery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
