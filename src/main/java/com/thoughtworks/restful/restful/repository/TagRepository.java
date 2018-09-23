package com.thoughtworks.restful.restful.repository;

import com.thoughtworks.restful.restful.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findByLabelAndUser_Id(String label, Long id);

    List<Tag> findByUser_IdOrderById(Long id);
}
