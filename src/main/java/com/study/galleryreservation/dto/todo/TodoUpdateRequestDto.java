package com.study.galleryreservation.dto.todo;

import com.study.galleryreservation.domain.todo.TodoCategory;
import com.study.galleryreservation.domain.todo.TodoPriority;
import com.study.galleryreservation.domain.todo.TodoStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter

@NoArgsConstructor
@AllArgsConstructor
//@Setter 대신 @NoArgsConstructor 권장
//RequestDTO는 Jackson 역직렬화를 위해
// @NoArgsConstructor + @AllArgsConstructor를 쓰는 게 일반적입니다

public class TodoUpdateRequestDto {


    private String title;

    private String description;

    private TodoCategory category;

    private String assignee;

    private TodoPriority priority;

    private TodoStatus status;

    private LocalDate dueDate;



}
