package com.study.galleryreservation.service;

import com.study.galleryreservation.domain.member.Member;
import com.study.galleryreservation.domain.todo.Todo;
import com.study.galleryreservation.dto.todo.TodoCreateRequestDto;
import com.study.galleryreservation.dto.todo.TodoResponseDto;
import com.study.galleryreservation.dto.todo.TodoUpdateRequestDto;
import com.study.galleryreservation.repository.MemberRepository;
import com.study.galleryreservation.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {
    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;
    public  List<Todo>getAll(){
        return todoRepository.findAll();
    }
    public TodoResponseDto findById(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 todo가 없습니다. id=" + id));
        return toResponseDto(todo);
    }

    //전체조회
    public List<TodoResponseDto>getAll(Long memberId){
        return todoRepository.findByMember_Id(memberId).stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    //완료 여부 필터 조회
    public List<TodoResponseDto>getAllByIsDone(Long memberId,boolean isDone){
        return todoRepository.findByMember_IdAndIsDone(memberId,isDone).stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    //제목 검색
    public List<TodoResponseDto>search(Long memberId,String keyword){
        return todoRepository.findByMember_IdAndTitleContaining(memberId,keyword).stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    //마감일 오름차순 조회
    public List<TodoResponseDto>getAllOrderByDueDate(Long memberId){
        return todoRepository.findByMember_IdOrderByDueDateAsc(memberId).stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
    //단건 조회
    public TodoResponseDto getOne(Long id){
        Todo todo=todoRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 일정이 없습니다.id="+id));
        return  toResponseDto(todo);

    }
    //생성
    @Transactional
    public TodoResponseDto create(Long memberId,TodoCreateRequestDto dto){
        Member member=memberRepository.findById(memberId)
                .orElseThrow(()->new IllegalArgumentException("해당맴버가없습니다.id="+dto.getMemberId()));
        LocalDateTime now = LocalDateTime.now();
        Todo todo=Todo.builder()
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

    //수정
    @Transactional
    public  TodoResponseDto update(Long id, Long memberId, TodoUpdateRequestDto dto){
        if(!todoRepository.existsByIdAndMember_Id(id,memberId)){
            throw new IllegalArgumentException("본인의 Todo만 수정할 수 있습니다.");
        }
        Todo todo=todoRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 Todo가 없습니다.id="+id));

        LocalDateTime now = LocalDateTime.now();
        Todo updated=Todo.builder()
                .id(todo.getId())
                .member(todo.getMember())
                .title(dto.getTitle())
                .content(dto.getContent())
                .isDone(dto.getIsDone())
                .dueDate(dto.getDueDate())
                .createdAt(todo.getCreatedAt())
                .updatedAt(now)
                .build();

        return  toResponseDto(todoRepository.save(updated));
    }

    //삭제
    @Transactional
    public void delete(Long id,Long memberId){

        if (!todoRepository.existsByIdAndMember_Id(id,memberId)){
            throw  new IllegalArgumentException("본인의 Todo만 삭제할 수 있습니다");
        }
        todoRepository.deleteById(id);
    }

     //Entity ->ResponseDto 로변환하는거
    private TodoResponseDto toResponseDto(Todo todo){
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
