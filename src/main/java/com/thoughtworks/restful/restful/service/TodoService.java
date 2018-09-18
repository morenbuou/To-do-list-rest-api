package com.thoughtworks.restful.restful.service;

import com.thoughtworks.restful.restful.model.Todo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class TodoService {

    private List<Todo> todoList = new ArrayList<>();

    public TodoService() {
        todoList.add(new Todo(UUID.randomUUID(), "meeting", "To Do", new Date()));
        todoList.add(new Todo(UUID.randomUUID(), "meeting with LY", "To Do", new Date()));
        todoList.add(new Todo(UUID.randomUUID(), "learn", "In progress", new Date()));
        todoList.add(new Todo(UUID.randomUUID(), "preparation", "Finished", new Date()));
    }

    public List<Todo> getTodoList() {
        return todoList;
    }

    public Todo getTodoById(UUID id) {
        return todoList.stream().filter(n -> n.getId().equals(id)).findFirst().get();
    }

    public Todo saveOrUpdate(Todo toDo) {
        if ("".equals(toDo.getId()) || toDo.getId() == null) {
            toDo.setId(UUID.randomUUID());
            todoList.add(toDo);
        }
        for (int i = 0; i < todoList.size(); i++) {
            if (todoList.get(i).getId().equals(toDo.getId())) {
                todoList.set(i, toDo);
            }
        }
        return toDo;
    }

    public void delete(UUID id) {
        for (int i = 0; i < todoList.size(); i++) {
            if (todoList.get(i).getId().equals(id)) {
                todoList.remove(i);
            }
        }
    }

}
