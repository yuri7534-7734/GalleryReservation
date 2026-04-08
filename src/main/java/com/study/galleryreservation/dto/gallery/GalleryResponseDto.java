package com.study.galleryreservation.dto.gallery;

import com.study.galleryreservation.domain.gallery.Gallery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
@Builder
public class GalleryResponseDto {
    private Long id;
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

    // 엔티티를 DTO로 변환하는 생성자
    public GalleryResponseDto(Gallery entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.location = entity.getLocation();
        this.floorZone = entity.getFloorZone();
        this.description = entity.getDescription();
        this.capacity = entity.getCapacity();
        this.isActive = entity.isActive();
        this.startTime = entity.getStartTime();
        this.endTime = entity.getEndTime();
        this.coverImageUrl = entity.getCoverImageUrl();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }
}