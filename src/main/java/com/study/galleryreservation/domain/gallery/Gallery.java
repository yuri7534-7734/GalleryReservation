package com.study.galleryreservation.domain.gallery;

import com.study.galleryreservation.domain.reservation.Reservation;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "gallery")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Gallery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "gallery", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Reservation> reservations = new ArrayList<>();

    @Column(name="name", nullable = false)
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "floor_zone", nullable = false, length = 10)
    private String floorZone;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "start_time", nullable = false)
    @Builder.Default
    private LocalTime startTime = LocalTime.of(10, 0);

    @Column(name = "end_time", nullable = false)
    @Builder.Default
    private LocalTime endTime = LocalTime.of(18, 0);

    @Column(name = "cover_image_url", length = 500)
    private String coverImageUrl;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public void update(String name, String location, String floorZone,
                       String description, Integer capacity, Boolean active) {
        this.name = name;
        this.location = location;
        if (floorZone != null && !floorZone.isBlank()) {
            this.floorZone = floorZone;
        }
        this.description = description;
        this.capacity = capacity;
        this.isActive = active != null && active;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * HTML에서 img src에 넣을 주소입니다.
     * 인터넷 주소(https://...)는 그대로 쓰고, 그 외에는 웹 루트 기준 경로(/로 시작)로 맞춥니다.
     */
    public String getCoverImageUrlForDisplay() {
        if (coverImageUrl == null || coverImageUrl.isBlank()) {
            return null;
        }
        String url = coverImageUrl.trim();
        if (url.startsWith("http://") || url.startsWith("https://") || url.startsWith("//")) {
            return url;
        }
        if (url.startsWith("/")) {
            return url;
        }
        return "/" + url;
    }
}
