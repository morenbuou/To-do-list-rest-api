package com.thoughtworks.restful.restful.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.restful.restful.model.Todo;
import com.thoughtworks.restful.restful.model.User;
import com.thoughtworks.restful.restful.service.TodoService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TodoListControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TodoService toDoService;

    Todo todoNew;
    @Before
    public void setUp() throws Exception {
        todoNew = new Todo(2l, "todoNew", "to do", new Date(), new HashSet<>(), 1l);
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "USER")
    public void getTodoList() throws Exception {
        Todo todo = new Todo(1l, "todo", "to do", new Date(), new HashSet<>(), 1l);

        List<Todo> todoList = Arrays.asList(todo, todoNew);

        given(toDoService.getTodoListByUserId(any())).willReturn(todoList);

        mvc.perform(
                get("/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("todo"))
                .andExpect(jsonPath("$[1].name").value("todoNew"));
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "USER")
    public void getTodoById() throws Exception {
        given(toDoService.getTodoByUserIdAndId(todoNew.getId())).willReturn(todoNew);

        mvc.perform(
                get("/todos/{id}", todoNew.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("todoNew"))
                .andExpect(jsonPath("$.status").value("to do"));
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "USER")
    public void addTodo() throws Exception {
        given(toDoService.saveOrUpdate(any())).willReturn(todoNew);

        mvc.perform(
                post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(todoNew)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("todoNew"));
        verify(toDoService, times(1)).saveOrUpdate(any());
        verifyNoMoreInteractions(toDoService);
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "USER")
    public void updateTodo() throws Exception {
        given(toDoService.getTodoById(todoNew.getId())).willReturn(todoNew);
        given(toDoService.saveOrUpdate(todoNew)).willReturn(todoNew);

        mvc.perform(
                put("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(todoNew)))
                .andExpect(status().isOk());

        verify(toDoService, times(1)).getTodoById(todoNew.getId());
        verify(toDoService, times(1)).saveOrUpdate(any());
        verifyNoMoreInteractions(toDoService);
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "USER")
    public void test_update_todo_fail_404_not_found() throws Exception {
        when(toDoService.getTodoById(todoNew.getId())).thenReturn(null);

        mvc.perform(
                put("/todos", todoNew.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(todoNew)))
                .andExpect(status().isNotFound());

        verify(toDoService, times(1)).getTodoById(todoNew.getId());
        verifyNoMoreInteractions(toDoService);
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "USER")
    public void deleteTodoById() throws Exception {
        when(toDoService.getTodoById(todoNew.getId())).thenReturn(todoNew);
        doNothing().when(toDoService).delete(todoNew.getId());

        mvc.perform(
                delete("/todos/{id}", todoNew.getId()))
                .andExpect(status().isOk());

        verify(toDoService, times(1)).getTodoById(todoNew.getId());
        verify(toDoService, times(1)).delete(todoNew.getId());
        verifyNoMoreInteractions(toDoService);
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "USER")
    public void test_delete_todo_fail_404_not_found() throws Exception {
        when(toDoService.getTodoById(todoNew.getId())).thenReturn(null);

        mvc.perform(
                delete("/todos/{id}", todoNew.getId()))
                .andExpect(status().isNotFound());

        verify(toDoService, times(1)).getTodoById(todoNew.getId());
        verifyNoMoreInteractions(toDoService);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}