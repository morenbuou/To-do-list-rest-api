package com.thoughtworks.restful.restful.repository;

import com.thoughtworks.restful.restful.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByName(String name);

    List<Todo> findByDate(Date date);

    List<Todo> findByTags_Value(String value);

    List<Todo> findByTags_ValueInOrderById(Set<String> values);

//    @Query("select t from todo t join tag u where u.value = :value")
//    List<Todo> findAllByTagValue(@Param("value") String value);

}
