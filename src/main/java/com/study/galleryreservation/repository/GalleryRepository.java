package com.study.galleryreservation.repository;

import com.study.galleryreservation.domain.gallery.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {
    Optional<Gallery> findByName(String name);
}