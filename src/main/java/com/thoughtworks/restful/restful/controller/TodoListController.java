package com.thoughtworks.restful.restful.controller;


import com.thoughtworks.restful.restful.controller.Exception.ForbiddenException;
import com.thoughtworks.restful.restful.controller.Exception.NotFoundException;
import com.thoughtworks.restful.restful.model.TagCriteria;
import com.thoughtworks.restful.restful.model.Todo;
import com.thoughtworks.restful.restful.model.User;
import com.thoughtworks.restful.restful.service.LoginService;
import com.thoughtworks.restful.restful.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoListController {

    @Autowired
    TodoService toDoService;

    @Autowired
    LoginService loginService;

    @GetMapping
    public List<Todo> getTodoListByUserId(Pageable pageable) {
        User user = getPrincipal();
        return toDoService.getTodoListByUserId(user.getId(), pageable);
    }

    @PostMapping(value = "/query")
    public List<Todo> getTodoListByCriteria(@RequestBody TagCriteria tagCriteria) {
        User user = getPrincipal();
        return toDoService.getTodoListByTagsAndDate(tagCriteria, user.getId());
    }

    @GetMapping(value = "/{id}")
    public Todo getTodoById(@PathVariable(value = "id") Long id) throws ForbiddenException {
        User user = getPrincipal();
        Todo todo = toDoService.getTodoByUserIdAndId(user.getId(), id);
        if (todo == null) {
            throw new ForbiddenException();
        }
        return todo;
    }

    @GetMapping(value = "/tagValue/{tagValue}")
    public List<Todo> getTodoByTagValue(@PathVariable(value = "tagValue") String value) {
        User user = getPrincipal();
        return toDoService.getTodoListByTodoTagValue(user.getId(), value);
    }

    @PostMapping
    public Todo addTodo(@RequestBody Todo todo) {
        User user = getPrincipal();
        todo.setUser(user);
        return toDoService.saveOrUpdate(todo);
    }

    @PutMapping
    public Todo updateTodo(@RequestBody Todo todo) throws NotFoundException {
        if (toDoService.getTodoById(todo.getId()) == null) {
            throw new NotFoundException();
        }
        User user = getPrincipal();
        todo.setUser(user);
        return toDoService.saveOrUpdate(todo);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteTodoById(@PathVariable(value = "id") Long id) throws NotFoundException {
        if (toDoService.getTodoById(id) == null) {
            throw new NotFoundException();
        }
        toDoService.delete(id);
    }

    private User getPrincipal() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
