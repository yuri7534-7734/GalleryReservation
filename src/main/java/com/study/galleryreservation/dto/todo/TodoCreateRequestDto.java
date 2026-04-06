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
    @Size(max = 200,message = "제목은 100자 이하여야합니다.")
    private String title;

    @NotBlank
    @Size(max = 200,message = "내용은 500자 이하여야합니다.")
    private String content;
    private boolean isDone;
    private LocalDate dueDate;
}
