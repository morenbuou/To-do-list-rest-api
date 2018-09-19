package com.thoughtworks.restful.restful.repository;

import com.thoughtworks.restful.restful.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findByLabel(String label);
}
