package com.thoughtworks.restful.restful.repository;

import com.thoughtworks.restful.restful.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByName(String name);

}
