package com.study.galleryreservation.controller;

import com.study.galleryreservation.dto.todo.TodoCreateRequestDto;
import com.study.galleryreservation.dto.todo.TodoUpdateRequestDto;
import com.study.galleryreservation.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {
    private final TodoService todoService;
    @GetMapping("/list")
    public String list(
            @RequestParam(required = false)Long memberId,
            @RequestParam(required = false)String keyword,
            @RequestParam(required = false)Boolean isDone,
            Model model){
        if (keyword!=null&&!keyword.isBlank()){
            model.addAttribute("todos",todoService.search(memberId,keyword));
        } else if (isDone != null) {
            model.addAttribute("todos",todoService.getAllByIsDone(memberId,isDone));
        }
else {
    model.addAttribute("todos",todoService.getAll(memberId));
        }
return "todo/list";
    }
    @GetMapping("/form")
    public String form(Model model){
        model.addAttribute("todoCreateRequestDto",new TodoCreateRequestDto());
        return "todo/form";
    }
    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         @RequestParam Long memberId,
                         @ModelAttribute TodoUpdateRequestDto dto){
        todoService.update(id,memberId,dto);
        return"redirect:/todo/list";
    }
    @PostMapping("delete/{id}")
    public String delete(@PathVariable Long id,
                         @RequestParam Long memberId){
        todoService.delete(id,memberId);
        return "redirect:/todo/list";
    }
}
