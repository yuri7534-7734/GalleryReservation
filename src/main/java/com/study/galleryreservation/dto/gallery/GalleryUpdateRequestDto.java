package com.study.galleryreservation.dto.gallery;

import com.study.galleryreservation.domain.gallery.Gallery;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GalleryUpdateRequestDto {

    private String name;
    private String location;
    private String floorZone;
    private String description;
    private Integer capacity;
    private Boolean active;

    public static GalleryUpdateRequestDto from(Gallery gallery) {
        return GalleryUpdateRequestDto.builder()
                .name(gallery.getName())
                .location(gallery.getLocation())
                .floorZone(gallery.getFloorZone())
                .description(gallery.getDescription())
                .capacity(gallery.getCapacity())
                .active(gallery.isActive())
                .build();
    }
}
