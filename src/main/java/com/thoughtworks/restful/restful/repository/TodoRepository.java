package com.thoughtworks.restful.restful.repository;

import com.thoughtworks.restful.restful.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {

}
