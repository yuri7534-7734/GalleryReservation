package com.study.galleryreservation.domain.todo;

import com.study.galleryreservation.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "todo")
@EntityListeners(AuditingEntityListener.class) //createdDate,LastModifiedDate 사용을위한 어노테이션
@Getter @Builder @AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    private Long id;

    @Column(name="title", nullable = false,length = 200)
    private String title;

    @Column(name="content")
    private String content;

    @Column(name="is_done",nullable = false)
    private boolean isDone;

    @Column(name = "due_date", updatable = false)
    private LocalDate dueDate;

    @CreatedDate //생성시간 자동처리
    @Column(name = "created_at", updatable = false,nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate //수정시간만 자동처리
    @Column(name = "updated_at",nullable = false )
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id",nullable = false)
    private Member member;





}
