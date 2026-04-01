package com.study.galleryreservation.dto.gallery;

import lombok.*;

import java.time.LocalTime;
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
    private LocalTime startTime;
    private LocalTime endTime;
    private String coverImageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
