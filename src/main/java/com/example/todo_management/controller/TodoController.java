package com.example.todo_management.controller;


import com.example.todo_management.dto.TodoDto;
import com.example.todo_management.service.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoDto> addTodo(@RequestBody TodoDto todoDto){
        return new ResponseEntity<>(todoService.addTodo(todoDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> getTodo(@PathVariable Long id){
        return ResponseEntity.ok(todoService.getTodo(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<TodoDto>> getAllTodos(){
        return ResponseEntity.ok(todoService.getAllTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoDto> updateTodo(@RequestBody TodoDto todoDto,@PathVariable Long id){
        return ResponseEntity.ok(todoService.updateTodo(todoDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable Long id){
        todoService.deleteTodo(id);
        return ResponseEntity.ok("Todo deleted Successfully.");
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<TodoDto> completeTodo(@PathVariable Long id){
        return ResponseEntity.ok(todoService.completeTodo(id));
    }

    @PatchMapping("/{id}/inComplete")
    public ResponseEntity<TodoDto> inCompleteTodo(@PathVariable Long id){
        return ResponseEntity.ok(todoService.inCompleteTodo(id));
    }
}
