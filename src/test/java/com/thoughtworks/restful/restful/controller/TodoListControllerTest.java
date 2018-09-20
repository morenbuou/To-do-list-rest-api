package com.thoughtworks.restful.restful.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.restful.restful.model.Todo;
import com.thoughtworks.restful.restful.model.User;
import com.thoughtworks.restful.restful.service.TodoService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.HashSet;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TodoListController.class)
public class TodoListControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TodoService toDoService;

    @Before
    public void setUp() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(testUser.getLogin(), testUser.getPassword()));
    }

    //    @Test
//    public void getTodoList() throws Exception {
//        Todo todo = new Todo(1l, "wjh", "finished", new Date());
//        Todo todoNew = new Todo(2l, "todoNew", "to do", new Date());
//
//        List<Todo> todoList = Arrays.asList(todo, todoNew);
//
//        given(toDoService.getTodoList()).willReturn(todoList);
//
//        mvc.perform(
//                get("/todos"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].name").value("wjh"))
//                .andExpect(jsonPath("$[1].name").value("todoNew"));
//    }

    @Test
    public void getTodoById() throws Exception {
        Todo todoNew = new Todo(1l, "todoNew", "to do", new Date(), new HashSet<>(), new User());

        given(toDoService.getTodoById(todoNew.getId())).willReturn(todoNew);

        mvc.perform(
                get("/todos/{id}", todoNew.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("todoNew"))
                .andExpect(jsonPath("$.status").value("to do"));
    }

    @Test
    public void addTodo() throws Exception {
        Todo todoNew = new Todo(1l, "todoNew", "to do", new Date(), new HashSet<>(), new User());

        given(toDoService.saveOrUpdate(any())).willReturn(todoNew);

        mvc.perform(
                post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(todoNew)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("todoNew"));
        verify(toDoService, times(1)).saveOrUpdate(todoNew);
        verifyNoMoreInteractions(toDoService);
    }

    @Test
    public void updateTodo() throws Exception {
        Todo todoNew = new Todo(1l, "todoNew", "to do", new Date(), new HashSet<>(), new User());

        given(toDoService.getTodoById(todoNew.getId())).willReturn(todoNew);
        given(toDoService.saveOrUpdate(todoNew)).willReturn(todoNew);

        mvc.perform(
                put("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(todoNew)))
                .andExpect(status().isOk());

        verify(toDoService, times(1)).getTodoById(todoNew.getId());
        verify(toDoService, times(1)).saveOrUpdate(todoNew);
        verifyNoMoreInteractions(toDoService);
    }

    @Test
    public void test_update_todo_fail_404_not_found() throws Exception {
        Todo todoNew = new Todo(1l, "todoNew", "to do", new Date(), new HashSet<>(), new User());

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
    public void deleteTodoById() throws Exception {
        Todo todoNew = new Todo(1l, "todoNew", "to do", new Date(), new HashSet<>(), new User());

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
    public void test_delete_todo_fail_404_not_found() throws Exception {
        Todo todoNew = new Todo(1l, "todoNew", "to do", new Date(), new HashSet<>(), new User());

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