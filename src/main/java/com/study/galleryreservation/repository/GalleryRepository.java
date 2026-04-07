package com.study.galleryreservation.repository;

import com.study.galleryreservation.domain.gallery.Gallery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {
    Optional<Gallery> findByName(String name);

    @Query("SELECT g FROM Gallery g WHERE LOWER(FUNCTION('replace', g.name, ' ', '')) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Gallery> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
}