package com.study.galleryreservation.dto.todo;

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

    private String title;

    private String content;

    private Boolean isDone;

    private LocalDate dueDate;



}
