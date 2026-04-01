package com.study.galleryreservation.dto.gallery;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GalleryUpdateRequestDto {

    private String name;
    private String location;
    private String floorZone;
    private String description;
    private Integer capacity;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
