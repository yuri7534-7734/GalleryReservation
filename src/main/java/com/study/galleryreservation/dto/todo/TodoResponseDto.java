package com.study.galleryreservation.dto.todo;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter @Builder
public class TodoResponseDto {
    private Long id;

    private String title;

    private String content;

    private boolean isDone;

    private LocalDate dueDate;

    private Long memberId;
}