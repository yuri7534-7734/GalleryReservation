package com.study.galleryreservation.dto.todo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
@NotBlank
@Size(max = 200, message = "제목은 200자 이하여야합니다.")
    private String title;

    private String content;

    private boolean isDone;

    private LocalDate dueDate;


}
