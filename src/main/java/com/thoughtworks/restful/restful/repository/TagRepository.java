package com.thoughtworks.restful.restful.repository;

import com.thoughtworks.restful.restful.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findByLabelAndUserId(String label, Long id);

    List<Tag> findByUserIdOrderById(Long id);
}
