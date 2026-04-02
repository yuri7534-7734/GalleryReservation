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

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "cover_image_url")
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
}
