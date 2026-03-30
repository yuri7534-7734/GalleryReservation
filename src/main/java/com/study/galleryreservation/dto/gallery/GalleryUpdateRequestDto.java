package com.study.galleryreservation.dto.gallery;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GalleryUpdateRequestDto {

    private String name;
    private String location;
    private String description;
    private Integer capacity;
    private boolean is_active;
}
