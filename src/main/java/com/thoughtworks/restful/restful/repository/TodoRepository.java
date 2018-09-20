package com.thoughtworks.restful.restful.repository;

import com.thoughtworks.restful.restful.model.Todo;
import com.thoughtworks.restful.restful.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    Todo findByIdAndUser_Id(User user, Long id);

    List<Todo> findByName(String name);

    List<Todo> findByTags_ValueAndUser_Id(User user, String value);

    List<Todo> findByUser(User user, Pageable pageable);

}
