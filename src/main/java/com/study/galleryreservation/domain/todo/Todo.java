package com.study.galleryreservation.domain.todo;

import com.study.galleryreservation.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "todo")
@Getter @Builder @AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
