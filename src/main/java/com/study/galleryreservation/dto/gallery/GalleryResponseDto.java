package com.study.galleryreservation.dto.gallery;

import com.study.galleryreservation.domain.gallery.Gallery;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter @Builder
public class GalleryResponseDto {
    private final Long id;
    private final String name;
    private final String location;
    private final String description;
    private final Integer capacity;
    private final boolean is_active;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    // 엔티티를 DTO로 변환하는 생성자
    public GalleryResponseDto(Gallery entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.location = entity.getLocation();
        this.description = entity.getDescription();
        this.capacity = entity.getCapacity();
        this.is_active = entity.is_active();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }
}
