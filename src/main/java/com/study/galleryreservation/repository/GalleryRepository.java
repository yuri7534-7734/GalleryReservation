package com.study.galleryreservation.repository;

import com.study.galleryreservation.domain.gallery.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {
}