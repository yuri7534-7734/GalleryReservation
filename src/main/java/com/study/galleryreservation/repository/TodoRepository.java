package com.study.galleryreservation.repository;

import com.study.galleryreservation.domain.todo.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
