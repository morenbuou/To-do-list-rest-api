package com.thoughtworks.restful.restful.repository;

import com.thoughtworks.restful.restful.model.Todo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

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

}