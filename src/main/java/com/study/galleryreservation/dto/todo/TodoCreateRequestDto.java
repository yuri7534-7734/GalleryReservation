package com.study.galleryreservation.dto.todo;

import com.study.galleryreservation.domain.todo.TodoCategory;
import com.study.galleryreservation.domain.todo.TodoStatus;
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

    private String description;

    private TodoCategory category;

    private String assignee;

    private TodoStatus status;

    private LocalDate dueDate;


}
