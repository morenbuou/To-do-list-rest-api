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

    public List<Todo> getTodoListByUser(User user, Pageable pageable) {
        return todoRepository.findByUser(user, pageable);
    }

    public Todo getTodoByUserAndId(User user, Long id) {
        return todoRepository.findByIdAndUser_Id(user, id);
    }

    public Todo getTodoById(Long id) {
        return todoRepository.findOne(id);
    }

    public List<Todo> getByTodoTagValue(User user, String value) {
        return todoRepository.findByTags_ValueAndUser_Id(user, value);
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
