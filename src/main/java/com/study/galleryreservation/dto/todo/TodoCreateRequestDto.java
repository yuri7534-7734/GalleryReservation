package com.study.galleryreservation.dto.todo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TodoCreateRequestDto {
    private Long memberId;

    private String title;

    private String content;

    private boolean isDone;

    private LocalDate dueDate;


}