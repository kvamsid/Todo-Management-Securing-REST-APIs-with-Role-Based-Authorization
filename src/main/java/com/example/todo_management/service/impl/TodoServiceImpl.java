package com.example.todo_management.service.impl;

import com.example.todo_management.dto.TodoDto;
import com.example.todo_management.entity.Todo;
import com.example.todo_management.exception.ResourceNotFoundException;
import com.example.todo_management.repository.TodoRepository;
import com.example.todo_management.service.TodoService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TodoServiceImpl implements TodoService {

    private TodoRepository todoRepository;
    private ModelMapper modelMapper;

    @Override
    public TodoDto addTodo(TodoDto todoDto) {
        Todo todo = modelMapper.map(todoDto, Todo.class);
        Todo savedTodo = todoRepository.save(todo);
        TodoDto savedTodoDto = modelMapper.map(savedTodo, TodoDto.class);
        return savedTodoDto;
    }

    @Override
    public TodoDto getTodo(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Todo not found with id: "+id));

        return modelMapper.map(todo, TodoDto.class);
    }

    @Override
    public List<TodoDto> getAllTodos() {
        List<Todo> todos = todoRepository.findAll();
        List<TodoDto> todoDtos = todos.stream().map((todo)-> modelMapper.map(todo, TodoDto.class)).collect(Collectors.toList());
        return todoDtos;
    }

    @Override
    public TodoDto updateTodo(TodoDto todoDto, Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Todo not found with id: "+id));
        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted(todo.isCompleted());
        Todo updatedTodo = todoRepository.save(todo);
        return modelMapper.map(updatedTodo, TodoDto.class);
    }

    @Override
    public void deleteTodo(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Todo not found with id: "+id));
        todoRepository.delete(todo);
    }

    @Override
    public TodoDto completeTodo(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Todo not found with id: "+id));
        todo.setCompleted(true);
        Todo savedTodo = todoRepository.save(todo);
        return modelMapper.map(savedTodo, TodoDto.class);
    }

    @Override
    public TodoDto inCompleteTodo(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Todo not found with id: "+id));
        todo.setCompleted(false);
        Todo savedTodo = todoRepository.save(todo);
        return modelMapper.map(savedTodo, TodoDto.class);
    }
}
