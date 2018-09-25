package com.thoughtworks.restful.restful.controller;


import com.thoughtworks.restful.restful.controller.exception.ForbiddenException;
import com.thoughtworks.restful.restful.controller.exception.NotFoundException;
import com.thoughtworks.restful.restful.model.TagCriteria;
import com.thoughtworks.restful.restful.model.Todo;
import com.thoughtworks.restful.restful.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoListController {

    @Autowired
    TodoService toDoService;

    @GetMapping
    public List<Todo> getTodoListByUserId(Pageable pageable) {
        return toDoService.getTodoListByUserId(pageable);
    }

    @PostMapping(value = "/query")
    public List<Todo> getTodoListByCriteria(@RequestBody TagCriteria tagCriteria) {
        return toDoService.getTodoListByTagsAndDate(tagCriteria);
    }

    @GetMapping(value = "/{id}")
    public Todo getTodoById(@PathVariable(value = "id") Long id) throws ForbiddenException {
        Todo todo = toDoService.getTodoByUserIdAndId(id);
        if (todo == null) {
            throw new ForbiddenException();
        }
        return todo;
    }

    @GetMapping(value = "/tagValue/{tagValue}")
    public List<Todo> getTodoByTagValue(@PathVariable(value = "tagValue") String value) {
        return toDoService.getTodoListByTodoTagValue(value);
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
