package com.thoughtworks.restful.restful.repository;

import com.thoughtworks.restful.restful.model.Todo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    Todo findByUser_IdAndId(Long userId, Long id);

    List<Todo> findByName(String name);

    List<Todo> findByUser_IdAndTags_Value(Long userId, String value);

    List<Todo> findByUser_Id(Long id, Pageable pageable);

}
