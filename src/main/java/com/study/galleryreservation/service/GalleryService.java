package com.study.galleryreservation.service;

import com.study.galleryreservation.domain.gallery.Gallery;
import com.study.galleryreservation.dto.gallery.GalleryCreateRequestDto;
import com.study.galleryreservation.dto.gallery.GalleryResponseDto;
import com.study.galleryreservation.repository.GalleryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GalleryService {
    private final GalleryRepository galleryRepository;

    public List<GalleryResponseDto> findAll(){
        List<Gallery> list = galleryRepository.findAll();

        return list.stream().map(GalleryResponseDto::new)
                .collect(Collectors.toList());
    }
    @Transactional
    public void galleryAdd(final GalleryCreateRequestDto dto){
        if(galleryRepository.findByName(dto.getName()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 갤러리 이름입니다.");
        }

        LocalDateTime now = LocalDateTime.now();
        LocalTime open = dto.getOpenTime() != null ? dto.getOpenTime() : LocalTime.of(10, 0);
        LocalTime close = dto.getCloseTime() != null ? dto.getCloseTime() : LocalTime.of(18, 0);
        Gallery gallery = Gallery.builder()
                .name(dto.getName())
                .location(dto.getLocation())
                .floorZone(dto.getFloorZone())
                .description(dto.getDescription())
                .capacity(dto.getCapacity())
                .isActive(true)
                .openTime(open)
                .closeTime(close)
                .createdAt(now)
                .updatedAt(now)
                .build();
        galleryRepository.save(gallery);
    }

    @Transactional
    public void galleryDelete(final Long id){
        Gallery gallery = galleryRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("작품을 찾을 수 없어 삭제가 불가능합니다."));

        galleryRepository.delete(gallery);
    }
}
