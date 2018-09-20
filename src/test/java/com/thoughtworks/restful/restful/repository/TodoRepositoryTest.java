package com.thoughtworks.restful.restful.repository;

import com.thoughtworks.restful.restful.model.Tag;
import com.thoughtworks.restful.restful.model.Todo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
public class TodoRepositoryTest {

    @Autowired
    TodoRepository todoRepository;

    @Test
    public void shouldFindTodoById() {
        todoRepository.save(Todo.builder().name("test").date(new Date()).status("to do").build());
        List<Todo> todoList = todoRepository.findByName("test");

        assertThat(todoList.size(), is(1));
        assertThat(todoList.get(0).getName(), is("test"));
    }

    @Test
    public void shouldFindTodoList() {
        todoRepository.save(Todo.builder().name("test").date(new Date()).status("to do").build());
        todoRepository.save(Todo.builder().name("test").date(new Date()).status("to do").build());
        List<Todo> todoList = todoRepository.findAll();

        assertThat(todoList.size(), is(2));
        assertThat(todoList.get(0).getName(), is("test"));
    }

    @Test
    public void shouldFindTagWhenSaveTodo() {
        Todo todo = Todo.builder().name("test").date(new Date()).status("to do").build();
        Tag meeting = Tag.builder().label("meeting").value("meeting").build();
        Tag activity = Tag.builder().label("activity").value("activity").build();
        Set<Tag> tags = new HashSet<>(Arrays.asList(meeting, activity));
        todo.setTags(tags);

        todoRepository.save(todo);
        List<Todo> todoList = todoRepository.findByName("test");

        assertThat(todoList.size(), is(1));
        assertThat(todoList.get(0).getTags().size(), is(2));
        assertThat(todoList.get(0).getName(), is("test"));
    }
}