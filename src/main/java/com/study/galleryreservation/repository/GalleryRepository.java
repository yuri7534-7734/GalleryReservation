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
    Page<Gallery> findByNameContainingIgnoreCaseOrLocationContainingIgnoreCase(String name, String location, Pageable pageable);

    //갤러리 관리에서 조회
    @Query("SELECT g FROM Gallery g WHERE LOWER(REPLACE(g.name, ' ', '')) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Gallery> findByNameIgnoreSpace(@Param("keyword") String keyword, Pageable pageable);
}