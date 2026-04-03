package com.study.galleryreservation.dto.gallery;

import com.study.galleryreservation.domain.gallery.Gallery;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GalleryCreateRequestDto {
    private String name;
    private String location;
    private String floorZone;
    private String description;
    private Integer capacity;
    private boolean isActive;
    private LocalTime startTime;
    private LocalTime endTime;
    private String coverImageUrl;

    // Service 레이어에서 엔티티로 변환할 때 사용
    public Gallery toEntity() {
        LocalTime start = startTime != null ? startTime : LocalTime.of(10, 0);
        LocalTime end = endTime != null ? endTime : LocalTime.of(18, 0);
        return Gallery.builder()
                .name(name)
                .location(location)
                .floorZone(floorZone != null && !floorZone.isBlank() ? floorZone : "1")
                .description(description)
                .capacity(capacity)
                .isActive(isActive)
                .startTime(start)
                .endTime(end)
                .coverImageUrl(coverImageUrl != null && !coverImageUrl.isBlank() ? coverImageUrl.trim() : null)
                .build();
    }
}