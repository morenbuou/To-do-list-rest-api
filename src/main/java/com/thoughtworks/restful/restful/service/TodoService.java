package com.thoughtworks.restful.restful.service;

import com.thoughtworks.restful.restful.model.Tag;
import com.thoughtworks.restful.restful.model.Todo;
import com.thoughtworks.restful.restful.model.User;
import com.thoughtworks.restful.restful.repository.TagRepository;
import com.thoughtworks.restful.restful.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TodoService {

    @Autowired
    TodoRepository todoRepository;

    @Autowired
    TagRepository tagRepository;

    public List<Todo> getTodoListByUserId(Long id, Pageable pageable) {
        return todoRepository.findByUser_Id(id, pageable);
    }

    public Todo getTodoByUserIdAndId(Long userId, Long id) {
        return todoRepository.findByUser_IdAndId(userId, id);
    }

    public Todo getTodoById(Long id) {
        return todoRepository.findOne(id);
    }

    public List<Todo> getByTodoTagValue(Long id, String value) {
        return todoRepository.findByUser_IdAndTags_Value(id, value);
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
}
