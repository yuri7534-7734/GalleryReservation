package com.study.galleryreservation.dto.todo;

import com.study.galleryreservation.domain.todo.TodoCategory;
import com.study.galleryreservation.domain.todo.TodoPriority;
import com.study.galleryreservation.domain.todo.TodoStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter @Builder
public class TodoResponseDto {
    private Long id;

    private String title;

   private String description;

   private TodoCategory category;

   private String assignee;

   private TodoPriority priority;

   private TodoStatus status;

    private LocalDate dueDate;

    private Long memberId;
}
