package com.study.galleryreservation.domain.todo;

import com.study.galleryreservation.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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

    @Column(name="text")
    private String text;

    @Column(name="is_done",nullable = false)
    private boolean is_done;



    @Column(name = "due_date", updatable = false)
    private LocalDate due_date;

    @CreatedDate //생성시간 자동처리
    @Column(name = "created_at", updatable = false)
    private LocalDateTime create_at;

    @LastModifiedDate //수정시간만 자동처리
    @Column(name = "updated_at", )
    private LocalDateTime updated_at;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id",nullable = false)
    private Member member;





}
