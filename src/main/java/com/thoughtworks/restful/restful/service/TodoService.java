package com.thoughtworks.restful.restful.service;

import com.thoughtworks.restful.restful.model.Tag;
import com.thoughtworks.restful.restful.model.Todo;
import com.thoughtworks.restful.restful.repository.TagRepository;
import com.thoughtworks.restful.restful.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TodoService {

    @Autowired
    TodoRepository todoRepository;

    @Autowired
    TagRepository tagRepository;

    public Page<Todo> getTodoList(Pageable pageable) {
        return todoRepository.findAll(pageable);
    }

    public Todo getTodoById(Long id) {
        return todoRepository.findOne(id);
    }

    public List<Todo> getByTodoTagValue(String value) {
        return todoRepository.findByTags_Value(value);
    }

    public List<Todo> getByTodoTagIn(Set<String> values) {
        return todoRepository.findByTags_ValueInOrderById(values);
    }

    public Todo save(Todo toDo) {
        return todoRepository.save(toDo);
    }

    public Todo update(Todo toDo) {
        return todoRepository.exists(toDo.getId()) ? todoRepository.save(toDo) : null;
    }

    public Todo saveOrUpdate(Todo toDo) {
        Set<Tag> tags = new HashSet<>();
        for (Tag tag: toDo.getTags()) {
            Tag item = tagRepository.findByLabel(tag.getLabel());
            tags.add(item != null ? item : tag);
        }
        toDo.setTags(tags);
        return todoRepository.save(toDo);
    }

    public void delete(Long id) {
        todoRepository.delete(id);
    }

    public Page<Todo> getTodoPage(Pageable pageable) {
        return todoRepository.findAll(pageable);
    }
}
