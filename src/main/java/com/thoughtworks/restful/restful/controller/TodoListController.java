package com.thoughtworks.restful.restful.controller;


import com.thoughtworks.restful.restful.controller.Exception.NotFoundException;
import com.thoughtworks.restful.restful.model.Todo;
import com.thoughtworks.restful.restful.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/todos")
public class TodoListController {

    @Autowired
    TodoService toDoService;

    @GetMapping
    public List<Todo> getTodoList() {
        return toDoService.getTodoList();
    }

    @GetMapping(value = "/{id}")
    public Todo getTodoById(@PathVariable(value = "id") UUID id) {
        return toDoService.getTodoById(id);
    }

    @PostMapping
    public Todo addTodo(@RequestBody Todo todo) {
        return toDoService.saveOrUpdate(todo);
    }

    @PutMapping
    public Todo updateTodo(@RequestBody Todo todo) throws NotFoundException {
        if (toDoService.getTodoById(todo.getId()) == null) {
            throw new NotFoundException();
        }
        return toDoService.saveOrUpdate(todo);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteTodoById(@PathVariable(value = "id") UUID id) throws NotFoundException {
        if (toDoService.getTodoById(id) == null) {
            throw new NotFoundException();
        }
        toDoService.delete(id);
    }
}
