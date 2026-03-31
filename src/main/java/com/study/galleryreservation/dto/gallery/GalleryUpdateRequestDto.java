package com.study.galleryreservation.dto.gallery;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GalleryUpdateRequestDto {
    // 수정하고 싶은 필드만 정의
    private String name;
    private String location;
    private String description;
    private Integer capacity;
    private boolean is_active;
}
