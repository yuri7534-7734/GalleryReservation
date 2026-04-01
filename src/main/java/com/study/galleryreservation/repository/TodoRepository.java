package com.study.galleryreservation.repository;

import com.study.galleryreservation.domain.todo.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    //특정맴버의 전체 Todo 조회
    List<Todo> findByMember_Id(Long memberId);


    //특정 맴버의 완료 여부 조회
    List<Todo>findByMember_IdAndIsDone(Long memberId,boolean isDone);


    //제목 검색
    List<Todo>findByMember_IdAndTitleContaining(Long memberId,String keyword);

    //마감일 오름차순 정렬
    List<Todo>findByMember_IdOrderByDueDateAsc(Long memberId);

    //보인 Todo여부 확인   true/false 여부만 확인하면되서 boolean사용
    boolean existsByIdAndMember_Id(Long id,Long memberId);




}