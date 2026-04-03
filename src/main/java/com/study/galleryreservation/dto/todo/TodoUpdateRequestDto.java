package com.study.galleryreservation.dto.todo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Setter 대신 @NoArgsConstructor 권장
//RequestDTO는 Jackson 역직렬화를 위해
// @NoArgsConstructor + @AllArgsConstructor를 쓰는 게 일반적입니다

public class TodoUpdateRequestDto {
@NotBlank
@Size(max = 200,message = "제목은 200자 이하여야합니다.")
    private String title;

    private String content;

    private boolean isDone;

    private LocalDate dueDate;



}
