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
    @Column(name="id",nullable = false)
    private Long id;

    @Column(name="title", nullable = false,length = 200)
    private String title;

    @Column(name="content", nullable = false,length = 2000)
    private String content;

    @Column(name="is_done",nullable = false)
    private boolean isDone;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "created_at", updatable = false,nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at",nullable = false )
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id",nullable = false)
    private Member member;



}
