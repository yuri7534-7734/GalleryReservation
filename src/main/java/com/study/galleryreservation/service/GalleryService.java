package com.study.galleryreservation.service;

import com.study.galleryreservation.domain.gallery.Gallery;
import com.study.galleryreservation.dto.gallery.GalleryResponseDto;
import com.study.galleryreservation.repository.GalleryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GalleryService {
    @Autowired
    private final GalleryRepository galleryRepository;

    public List<GalleryResponseDto> findAll(){
        List<Gallery> list = galleryRepository.findAll();

        return list.stream().map(GalleryResponseDto::new)
                .collect(Collectors.toList());
    }

}
