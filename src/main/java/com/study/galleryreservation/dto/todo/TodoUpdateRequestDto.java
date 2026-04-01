package com.study.galleryreservation.dto.todo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TodoUpdateRequestDto {

    private String title;

    private String content;

    private Boolean isDone;

    private LocalDate dueDate;



}