package com.thoughtworks.restful.restful.service;

import com.thoughtworks.restful.restful.model.Todo;
import com.thoughtworks.restful.restful.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

    @Autowired
    TodoRepository todoRepository;

    public Page<Todo> getTodoList(Pageable pageable) {
        return todoRepository.findAll(pageable);
    }

    public Todo getTodoById(Long id) {
        return todoRepository.findOne(id);
    }

    public Todo save(Todo toDo) {
        return todoRepository.save(toDo);
    }

    public Todo update(Todo toDo) {
        return todoRepository.exists(toDo.getId()) ? todoRepository.save(toDo) : null;
    }

    public Todo saveOrUpdate(Todo toDo) {
        return todoRepository.save(toDo);
    }

    public void delete(Long id) {
        todoRepository.delete(id);
    }

    public Page<Todo> getTodoPage(Pageable pageable) {
        return todoRepository.findAll(pageable);
    }
}
