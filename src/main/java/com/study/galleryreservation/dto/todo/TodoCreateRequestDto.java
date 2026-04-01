package com.study.galleryreservation.dto.todo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TodoCreateRequestDto {
    private Long memberId;

    private String title;

    private String content;

    private boolean isDone;

    private LocalDate dueDate;


}
