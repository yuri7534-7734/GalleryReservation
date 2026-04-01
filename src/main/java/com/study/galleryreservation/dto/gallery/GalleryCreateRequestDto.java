package com.study.galleryreservation.dto.gallery;

import com.study.galleryreservation.domain.gallery.Gallery;
import lombok.*;

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

    // Service 레이어에서 엔티티로 변환할 때 사용
    public Gallery toEntity() {
        return Gallery.builder()
                .name(name)
                .location(location)
                .floorZone(floorZone != null && !floorZone.isBlank() ? floorZone : "1")
                .description(description)
                .capacity(capacity)
                .isActive(isActive)
                .build();
    }
}
