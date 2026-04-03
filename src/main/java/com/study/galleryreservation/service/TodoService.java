package com.study.galleryreservation.service;

import com.study.galleryreservation.domain.member.Member;
import com.study.galleryreservation.domain.todo.Todo;
import com.study.galleryreservation.dto.todo.TodoCreateRequestDto;
import com.study.galleryreservation.dto.todo.TodoResponseDto;
import com.study.galleryreservation.dto.todo.TodoUpdateRequestDto;
import com.study.galleryreservation.repository.MemberRepository;
import com.study.galleryreservation.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {
    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;

    // 전체 조회
    public Page<TodoResponseDto> getAll(Long memberId, Pageable pageable) {
        return todoRepository.findByMember_Id(memberId, pageable)
                .map(this::toResponseDto);
    }

    // 완료 여부 필터 조회
    public Page<TodoResponseDto> getAllByIsDone(Long memberId, boolean isDone, Pageable pageable) {
        return todoRepository.findByMember_IdAndIsDone(memberId, isDone, pageable)
                .map(this::toResponseDto);
    }

    // 제목 검색
    public Page<TodoResponseDto> search(Long memberId, String keyword, Pageable pageable) {
        return todoRepository.findByMember_IdAndTitleContaining(memberId, keyword, pageable)
                .map(this::toResponseDto);
    }

    // 마감일 오름차순 조회
    public Page<TodoResponseDto> getAllOrderByDueDate(Long memberId, Pageable pageable) {
        return todoRepository.findByMember_IdOrderByDueDateAsc(memberId, pageable)
                .map(this::toResponseDto);
    }

    // 단건 조회
    public TodoResponseDto getOne(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 없습니다. id=" + id));
        return toResponseDto(todo);
    }

    // findById (update폼에서 사용)
    public TodoResponseDto findById(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 todo가 없습니다. id=" + id));
        return toResponseDto(todo);
    }

    // 생성
    @Transactional
    public TodoResponseDto create(Long memberId, TodoCreateRequestDto dto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 없습니다. id=" + memberId));
        LocalDateTime now=LocalDateTime.now();
        Todo todo = Todo.builder()
                .member(member)
                .title(dto.getTitle())
                .content(dto.getContent())
                .isDone(false)
                .dueDate(dto.getDueDate())
                .createdAt(now)
                .updatedAt(now)
                .build();
        return toResponseDto(todoRepository.save(todo));
    }

    // 수정
    @Transactional
    public TodoResponseDto update(Long id, Long memberId, TodoUpdateRequestDto dto) {
        if (!todoRepository.existsByIdAndMember_Id(id, memberId)) {
            throw new IllegalArgumentException("본인의 Todo만 수정할 수 있습니다.");
        }
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 Todo가 없습니다. id=" + id));

        LocalDateTime now=LocalDateTime.now();
        Todo updated = Todo.builder()
                .id(todo.getId())
                .member(todo.getMember())
                .title(dto.getTitle())
                .content(dto.getContent())
                .isDone(dto.isDone())       // ← getIsDone() → isDone() 으로 변경
                .dueDate(dto.getDueDate())
                .build();
        return toResponseDto(todoRepository.save(updated));
    }

    // 삭제
    @Transactional
    public void delete(Long id, Long memberId) {
        if (!todoRepository.existsByIdAndMember_Id(id, memberId)) {
            throw new IllegalArgumentException("본인의 Todo만 삭제할 수 있습니다.");
        }
        todoRepository.deleteById(id);
    }

    // Entity -> ResponseDto 변환
    private TodoResponseDto toResponseDto(Todo todo) {
        return TodoResponseDto.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .content(todo.getContent())
                .isDone(todo.isDone())
                .dueDate(todo.getDueDate())
                .memberId(todo.getMember().getId())
                .build();
    }
}