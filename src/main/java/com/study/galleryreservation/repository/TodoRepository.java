package com.study.galleryreservation.repository;

import com.study.galleryreservation.domain.todo.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    //        특정맴버의 전체 Todo 조회
    List<Todo> findByMember_Id(Long memberId);


    //  특정맴버의 완료여부로 조회
    List<Todo> findByMember_IdAndIsDone(Long memberId, boolean isDone);

    // 마감 기한 일로 조회
    List<Todo>findByMember_IdAndDueDate(Long memberId, LocalDate dueDate);





}
