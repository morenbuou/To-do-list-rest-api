package com.thoughtworks.restful.restful.controller;


import com.thoughtworks.restful.restful.controller.Exception.NotFoundException;
import com.thoughtworks.restful.restful.model.Todo;
import com.thoughtworks.restful.restful.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todos")
public class TodoListController {

    @Autowired
    TodoService toDoService;

    @GetMapping
    public Page<Todo> getTodoList(Pageable pageable) {
        return toDoService.getTodoList(pageable);
    }

    @GetMapping(value = "/page/{page}/pageSize/{pageSize}")
    public Page<Todo> getTodoListPage(@PathVariable Integer page, @PathVariable Integer pageSize) {
        Pageable pageable = new PageRequest(page - 1, pageSize);
        return toDoService.getTodoPage(pageable);
    }

    @GetMapping(value = "/{id}")
    public Todo getTodoById(@PathVariable(value = "id") Long id) {
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
    public void deleteTodoById(@PathVariable(value = "id") Long id) throws NotFoundException {
        if (toDoService.getTodoById(id) == null) {
            throw new NotFoundException();
        }
        toDoService.delete(id);
    }
}
