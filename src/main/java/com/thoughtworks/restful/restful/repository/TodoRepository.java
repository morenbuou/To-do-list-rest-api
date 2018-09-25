package com.thoughtworks.restful.restful.repository;

import com.thoughtworks.restful.restful.model.Todo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long>, JpaSpecificationExecutor<Todo> {

    Todo findByUserIdAndId(Long userId, Long id);

    List<Todo> findByName(String name);

    List<Todo> findByUserIdAndTags_Value(Long userId, String value);

    List<Todo> findByUserId(Long id, Pageable pageable);

}
